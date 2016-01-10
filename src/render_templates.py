import os

from tempita import Template


def render_template(infile_name, outfile_name):
    with open(infile_name) as infile, open(outfile_name, 'w') as outfile:
        outfile.write(Template(infile.read()).substitute())


def main():
    for root, _, filenames in os.walk("."):
        for filename in filenames:
            filename = os.path.join(root, filename)
            suffix = '.template'
            if filename.endswith(suffix):
                render_template(filename, filename[:-len(suffix)])


if __name__ == '__main__':
    main()
