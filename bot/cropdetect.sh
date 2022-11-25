ffmpeg -i $1 -t 1 -vf cropdetect=32:16:0 -f null - 2>&1|awk '/crop/{print $NF}'|tail -n1| cut -d ':' -f 3,4
