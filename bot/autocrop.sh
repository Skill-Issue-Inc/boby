#!/bin/sh
#ffmpeg_zoom ver 20180128202453
I="$@";X=${I##*.};O=${I%.*}_autocrop.${X};f=$(which ffmpeg 2>/dev/null)
if [ ! "$f" ]||[ "$f" = '' ];then echo "Install ffmpeg";exit 1;fi
C=$($f -i "$I" -t 1 -vf cropdetect=32:16:0 -f null - 2>&1|awk '/crop/{print $NF}'|tail -n1)
echo $f -i "$I" -vf "$C" "$O"; $f -i "$I" -vf "$C" "$O"
