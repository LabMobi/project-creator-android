require 'highline'
require 'erb'
require 'yaml'

load 'lib/android'

# i for interface
i = HighLine.new

def get_binding
  binding
end

namespace :android do
  task :ask_stuff do
    output_folder = fetch(:path, nil)
    project_name = fetch(:name, nil)
    sanitized_name = fetch(:sanitized, nil)
    package = fetch(:package, nil)

    loop do
      if (output_folder or "").strip.empty?
        output_folder = i.ask('Relative path to output? ')
      else
        break
      end
    end

    loop do
      if (project_name or "").strip.empty?
        project_name = i.ask('Project name? (no trailing -android) ')
      else
        break
      end
    end

    if (sanitized_name or "").strip.empty?
      sanitized_name = project_name.gsub(/[^A-Za-z0-9]/, '')
    end

    if (package or "").strip.empty?
      package = "mobi.lab.#{sanitized_name.downcase}"
    end

    ok = false
    while not ok
      puts "You have chosen the name #{project_name}"
      puts "Current names are:\n"
      puts "package:     #{package}"
      puts "sanitized:   #{sanitized_name}"
      puts "name:        #{project_name}"

      i.choose do |menu|
        menu.prompt = 'This looks ok to you?  '
        menu.choice(:yes) do
          puts 'Cool.'
          ok = true
        end
        menu.choice(:no) do
          package = i.ask('Package: ') { |q| q.default = package }
          sanitized_name = i.ask('Sanitized name: ') { |q| q.default = sanitized_name }
          project_name = i.ask('Project name: ') { |q| q.default = project_name }
        end
      end
    end

    set :output_folder, output_folder
    set :project_name, project_name
    set :sanitized_name, sanitized_name
    set :package, package
  end

  task :project do
    set :template, 'project'
    o = [('a'..'z'), ('A'..'Z')].map { |i| i.to_a }.flatten
    # generate key pass for release
    set :keypass_release, (0...50).map { o[rand(o.length)] }.join
    # generate key pass for debug
    set :keypass_debug, (0...50).map { o[rand(o.length)] }.join
    # generate store pass for release
    set :storepass_release, (0...50).map { o[rand(o.length)] }.join
    # generate store pass for debug
    set :storepass_debug, (0...50).map { o[rand(o.length)] }.join
    proj = Android.new self
    proj.process
  end

  task :library_project do
    set :template, 'projectlib'
    proj = Android.new self
    proj.process_library
  end

  desc 'Create a new shiny android project'
  task :create do
  end

  desc 'Create a new shiny android library project'
  task :create_library do
  end
end

before 'android:project', 'android:ask_stuff'
before 'android:create', 'android:project'

before 'android:library_project', 'android:ask_stuff'
before 'android:create_library', 'android:library_project'
