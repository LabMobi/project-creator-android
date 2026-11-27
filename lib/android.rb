require 'fileutils'
require 'pathname'
require 'tmpdir'

class Android
  SIGNING_PLACEHOLDERS = {
    'TEMPLATE_RELEASE_STORE_PASS' => :storepass_release,
    'TEMPLATE_RELEASE_KEY_PASS' => :keypass_release,
    'TEMPLATE_DEBUG_STORE_PASS' => :storepass_debug,
    'TEMPLATE_DEBUG_KEY_PASS' => :keypass_debug
  }.freeze

  TEXT_FILE_PATTERN = /\.(xml|properties|java|kt|gradle|txt|md|yaml|pro)\z/.freeze

  def initialize(context)
    @context = context
  end

  def process
    path = @context.output_folder
    FileUtils.mkdir_p(path)

    create_project(path)
  end

  def process_library
    path = @context.output_folder
    FileUtils.mkdir_p(path)

    create_library_project(path)
  end

  def sign_project(path)
    signing_dir = File.join(path, 'app', 'signing')
    FileUtils.rm_rf(signing_dir)
    FileUtils.mkdir_p(signing_dir)

    generate_keystore(
      File.join(signing_dir, 'signing.keystore'),
      @context.storepass_release,
      @context.keypass_release
    )
    generate_keystore(
      File.join(signing_dir, 'debug.keystore'),
      @context.storepass_debug,
      @context.keypass_debug
    )
  end

  def generate_keystore(keystore_path, storepass, keypass)
    cmd = [
      'keytool', '-genkey',
      '-storetype', 'jks',
      '-alias', @context.sanitized_name,
      '-keyalg', 'RSA',
      '-keystore', keystore_path,
      '-keysize', '2048',
      '-storepass', storepass,
      '-keypass', keypass,
      '-dname', "CN=#{@context.project_name}, OU=Mobi Lab, C=EE",
      '-validity', '36500'
    ]
    raise "Failed to generate keystore at #{keystore_path}" unless system(*cmd)
  end

  def excluded_path?(file)
    rel = file.sub(/\A#{Regexp.escape(get_template)}\/?/, '')
    return false if rel.empty?

    parts = rel.split('/')
    return true if rel == 'app/signing' || rel.start_with?('app/signing/')
    return true if rel == 'app/google-services.json'
    return true if parts.any? { |p| %w[.gradle .kotlin .artifacts].include?(p) }
    return true if parts.last == 'build' && parts.length <= 2
    return true if parts.last == 'local.properties' || parts.last == '.DS_Store' || parts.last.end_with?('.iml')
    return true if parts.first == '.idea' && parts.length > 1 && parts[1] != 'inspectionProfiles'

    false
  end

  def apply_template(file, tmppath)
    return if excluded_path?(file)

    puts "Applying template to #{file}"
    if File.directory?(file)
      Dir.new(file).each do |f|
        next if f == '..' || f == '.'
        apply_template("#{file}/#{f}", tmppath)
      end
    else
      out_path = output_path(file, tmppath)
      puts "Mkdir file=#{file} path=#{File.dirname(out_path)}"
      FileUtils.mkdir_p(File.dirname(out_path))

      if file =~ TEXT_FILE_PATTERN
        result = File.read(file, encoding: 'UTF-8')
        result = ERB.new(result).result(@context.get_binding)
        SIGNING_PLACEHOLDERS.each do |placeholder, key|
          value = @context.fetch(key, nil)
          result.gsub!(placeholder, value) if value
        end
        result.gsub!(/ProjectCodeName/, @context.sanitized_name)
        result.gsub!(/mobi\.lab\.sample/, @context.package)
        File.write(out_path, result)
      else
        FileUtils.cp(file, out_path)
      end
    end
  end

  def output_path(file, tmppath)
    out = file.sub(/\A#{Regexp.escape(get_template)}\//, '')
    out.gsub!(/mobi\/lab\/sample/, @context.package.gsub(/\./, '/'))
    out.gsub!(/ProjectCodeName/, @context.sanitized_name)
    File.expand_path(out, tmppath)
  end

  def get_template
    @context.template
  end

  def verify_project_signing(path)
    build_gradle = File.read(File.join(path, 'app', 'build.gradle'), encoding: 'UTF-8')
    SIGNING_PLACEHOLDERS.each_key do |placeholder|
      raise "Unreplaced signing placeholder #{placeholder} in generated app/build.gradle" if build_gradle.include?(placeholder)
    end

    google_services = File.join(path, 'app', 'google-services.json')
    raise "Template google-services.json should not be present in generated project" if File.exist?(google_services)

    %w[signing.keystore debug.keystore].each do |name|
      generated = File.join(path, 'app', 'signing', name)
      template = File.join(get_template, 'app', 'signing', name)
      raise "Missing generated keystore #{generated}" unless File.exist?(generated) && File.size(generated) > 0
      if File.exist?(template) && FileUtils.compare_file(generated, template)
        raise "Generated keystore #{generated} must not match template keystore"
      end
    end
  end

  def create_project(path)
    apply_template(get_template, path)
    sign_project(path)
    verify_project_signing(path)
  end

  def create_library_project(path)
    apply_template(get_template, path)
  end
end
