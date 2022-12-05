from os import walk

# folder path
dir_path = r'./'

# list to store files

res = []
for (dir_path, dir_names, file_names) in walk(dir_path):
    for filen in file_names:
        if("git" not in dir_path and ".java" in filen):
            file = open(dir_path+"/"+filen,"r")
            lines = file.readlines()
            lines[0] = "package "+dir_names
            file = open(dir_path+"/"+filen,"w")
            file.write(lines)
