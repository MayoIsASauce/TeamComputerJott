from sys import argv
import re

def __usage():
    print("""Usage: python3 build.py <project_phase>
format: phase# OR p# OR #\n""")
    
def __test_improper() -> bool:
    """Test if the script has received properly formatted args
    
    * Returns True if the args are improper else False
    """
    # test if we have all the args
    under_sized = len(argv) < 2

    # test if the args are properly formatted
    tests: bool = under_sized
    if not under_sized:
        not_full = re.search(r'^phase[0-9]$', argv[1]) == None 
        not_mini = not argv[1].isdigit()
        not_small = re.search(r'^p[0-9]$', argv[1]) == None

        tests = tests or (not_full and not_small and not_mini)

    return tests

if __test_improper():
    __usage()
    quit(0x01)


from os import system


testers = [] # test cases to be run
prefix = [] # prepare the environment

if argv[1] in ['phase1', 'p1', '1']: 
    prefix.append("cd Src")
    testers.append("testers/JottTokenizerTester.java")
else:
    print(f"phase {argv[1][-1]} not implemented")
    exit(0x02)

# combine all of the prefixes into a long command
prepare = (" " if len(prefix) != 0 else "").join(prefix) + (" &&" if len(prefix) != 0 else "")

# combine all of files into a compile friendly format
testers.insert(0, "javac")
compile = (" " if len(testers) != 0 else "").join(testers) + (" &&" if len(testers) != 0 else "")

# combine all of the build files into a build friendly format
testers.remove("javac")
testers.insert(0, "java")
build = (" " if len(testers) != 0 else "").join(testers)

if (argv.__contains__("-v")):
    print(f"{prepare} {compile} {build}")

system(f"{prepare} {compile} {build}") # run compile and build