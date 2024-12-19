import os


def sanitize_file_name(inp):
    out = []
    inp = list(inp)
    for char in inp:
        if char not in " %()":
            out.append(char)
    return "".join(out)


PATH = "../tracks"

for filename in os.listdir(PATH):
    if (not filename.endswith(".mp3")):
        continue
    print("Converting ", filename)
    sanitized_name = sanitize_file_name(filename.split(".mp3")[0])
    command = f'mpg123 -w "{PATH}/{sanitized_name}.waw" "{PATH}/{filename}"'
    print("Command:", command)
    os.system(command)
    os.system(f'rm "{PATH}/{filename}"')
