import os

def padSpaces(inp):
    out = []
    inp = list(inp)
    for char in inp:        
        if char == " " or char == "&":
            out.append("\\")

        out.append(char)
    return "".join(out)

path = "../tracks"

for filename in os.listdir(path):
    if(not filename.endswith(".mp3")):
        continue;
    print("Converting ",filename)
    name = filename.split(".mp3")[0]
    name = padSpaces(name)
    command = "mpg123 -w ../tracks/" + name + ".waw ../tracks/" + padSpaces(filename)
    print("Command:", command)
    os.system(command)
    os.system("rm ../tracks/" + padSpaces(filename))