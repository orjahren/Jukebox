import os
import sys

link = sys.argv[1]

os.system("yt-dlp --extract-audio --audio-format mp3 " + link)
os.system("mv *.mp3 ../tracks/")
