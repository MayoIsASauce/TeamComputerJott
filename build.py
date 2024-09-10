from sys import argv
import re

size_test = len(argv) < 2

long_regex = re.search("phase[0-9]{1}", argv[1]) == None
mid_regex = re.search("p[0-9]{1}", argv[1]) == None
small_regex = re.search("[0-9]{1}", argv[1]) == None
regex_test = long_regex and mid_regex and small_regex

if size_test or regex_test:
    print("""Usage: python3 build.py <project_phase>
format: phase# OR p# OR #""")
    exit(0x01)

from os import system


testers = []
prefix = []

if argv[1] in ['phase1', 'p1', '1']: 
    prefix.append("cd Src")
    testers.append("testers/JottTokenizerTester.java")
else:
    print(f"phase {argv[1][-1]} not implemented")
    exit(0x02)

prepare = (" " if len(prefix) != 0 else "").join(prefix) + (" &&" if len(prefix) != 0 else "")

testers.insert(0, "javac")
compile = (" " if len(testers) != 0 else "").join(testers) + (" &&" if len(testers) != 0 else "")

testers.remove("javac")
testers.insert(0, "java")
build = (" " if len(testers) != 0 else "").join(testers)

if (argv.__contains__("-v")):
    print(f"{prepare} {compile} {build}")

system(f"{prepare} {compile} {build}")