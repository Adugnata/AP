import os
import re
import sys
import urllib.request
from subprocess import *


class Refactor:
    file_name = ""
    url = ""

    def __init__(self, url, file_name):
        self.file_name = file_name
        self.url = url

    def download(self):
        try:
            urllib.request.urlretrieve(self.url, self.file_name)  # download file
        #  catch URL errors
        except (urllib.error.URLError, urllib.error.HTTPError, ValueError):
            print("The URL is invalid.")
            sys.exit()
        except urllib.error.ContentTooShortError:
            print("Incorrect download of the file.")
            sys.exit()

    def snippet(self):
        # check if the snippet path is valid
        if not os.path.isfile(sys.argv[2]):
            print("The snippet does not exist.")
            sys.exit()

        # open the snippet, then read the lines and add a \t prefix for readability purpose
        snippet = open(sys.argv[2])
        snippet_lines = snippet.readlines()
        snippet_lines_tab = ["\t" + x for x in snippet_lines]
        snippet.close()

        # open and read the java file
        Java_file = open(self.file_name, 'r', encoding='utf-8')
        my_file_lines = Java_file.readlines()
        Java_file.close()

        # get the method name from the snippet
        met = re.search(' (\S+)\(', snippet_lines[0])
        if met:
            method_name = met.group(1)

        # insert the snippet in the file lines variable
        end_line_pattern = re.compile('}\s*')
        end_index = -1
        end_line = my_file_lines[end_index]
        while not end_line_pattern.match(end_line):
            end_index -= 1
            end_line = my_file_lines[end_index]
        my_file_lines = my_file_lines[:end_index]
        my_file_lines.extend(snippet_lines_tab)
        my_file_lines.append("}")

        # wrap println arguments with method overwrite the file with the file lines variable
        Java_file = open(self.file_name, 'w', encoding='UTF-8')
        println_string = re.compile('.*println\(.+\)')
        for line in my_file_lines:
            if println_string.match(line):
                line = re.sub('(.*)println\((.+)\)', r'\1println(' + method_name + r'(\2))', line)
            Java_file.write("%s" % line)
        Java_file.close()

    def compile(self):
        out, err = Popen(['javac', self.file_name], stdout=PIPE, stderr=PIPE).communicate()
        if err:
            print('Compilation failed. Please check COMP_ERR.txt for details')
            er = open("COMP_ERR.txt", 'wb')
            er.write(err)
            er.close()

    def execute(self):
        out, err = Popen(['java', self.file_name.replace('.java', "")], stdout=PIPE, stderr=PIPE).communicate()
        if out:
            f = open("OUTPUT.txt", 'wb')
            f.write(out)
            f.close()
        if err:
            er = open("ERROR.txt", 'wb')
            er.write(err)
            er.close()


def main():
    if len(sys.argv) < 3:
        print("Too few arguments.")
        sys.exit()
    url = sys.argv[1]
    file_name = os.path.basename(url)
    ref = Refactor(url, file_name)
    ref.download()
    ref.snippet()
    ref.compile()
    ref.execute()


if __name__ == "__main__":
    main()
