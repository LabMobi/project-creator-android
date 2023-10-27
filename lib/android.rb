require 'fileutils'
require 'pathname'
require 'tmpdir'

class Android
  def initialize(context)
    @context = context
  end

  def process
    path = @context.output_folder
    FileUtils::mkdir_p path

    create_project path
  end

  def process_library
    path = @context.output_folder
    FileUtils::mkdir_p path

    create_library_project path
  end

  def sign_project(path)
    `mkdir #{path}/app/signing`
    # Release keystore
    `keytool -genkey -storetype jks -alias #{@context.sanitized_name} -keyalg RSA -keystore #{path}/app/signing/signing.keystore -keysize 2048 -storepass #{@context.storepass_release} -keypass #{@context.keypass_release} -dname "CN=#{@context.project_name}, OU=Mobi Lab, C=EE" -validity 36500`
    # Debug keystore
    `keytool -genkey -storetype jks -alias #{@context.sanitized_name} -keyalg RSA -keystore #{path}/app/signing/debug.keystore -keysize 2048 -storepass #{@context.storepass_debug} -keypass #{@context.keypass_debug} -dname "CN=#{@context.project_name}, OU=Mobi Lab, C=EE" -validity 36500`
  end

  def apply_template(file, tmppath)
    puts "Applying template to #{file}"
    if File.directory? file
      Dir.new(file).each do |f|
        next if f == '..' || f == '.'
        apply_template file + '/' + f, tmppath
      end
    else
      result = File.read(file)
      if file =~ /(xml|properties|java|kt|gradle|txt|md|yaml|pro)$/
        result = ERB.new(result).result(@context.get_binding)
        result.gsub! /ProjectCodeName/, @context.sanitized_name
        result.gsub! /mobi\.lab\.sample/, @context.package
      end
      out_path = output_path(file, tmppath)
      puts "Mkdir file=#{file} path=#{File.dirname(out_path)}"
      FileUtils::mkdir_p File.dirname(out_path)
      File.write(out_path, result)
    end
  end

  def output_path(file, tmppath)
    out = file.gsub /^(#{get_template})\//, ""
    out.gsub! /mobi\/lab\/sample/, @context.package.gsub(/\./, "/")
    out.gsub! /ProjectCodeName/, @context.sanitized_name
    File.expand_path(out, tmppath)
  end

  def get_template
    @context.template
  end

  def create_project(path)
    apply_template get_template, path
    sign_project path
  end

  def create_library_project(path)
    apply_template get_template, path
  end
end
