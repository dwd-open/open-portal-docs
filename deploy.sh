#/bin/bash
git pull && \
rm -rf _book && \
gitbook build && \
cd _book && \
rm deploy.sh && \
datetime=`date +"%Y%m%d%H%M%S"` && \
filename=open-portal-docs_v$datetime.zip && \
zip -r $filename * && \
ossutil64 cp ./$filename oss://dwd-open-bucket/open-portal-static/website_zip/ && \
echo "\n\nhttps://dwd-open-bucket.oss-cn-hangzhou.aliyuncs.com/open-portal-static/website_zip/$filename\n"
