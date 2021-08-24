import os, os.path
from google.cloud import storage

def upload_blob(bucket_name, source_file_name, destination_blob_name):
    storage_client = storage.Client()
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(destination_blob_name)
    blob.upload_from_filename(source_file_name)

    print(
        "File {} uploaded to {}.".format(
            source_file_name, destination_blob_name
        )
    )

def uplode_mining(barcode):
    # 환경변수 추가
    os.environ["GOOGLE_APPLICATION_CREDENTIALS"]="ttks-161718-75e94a47cce4.json"
    upload_blob(
        bucket_name="ttks-161718.appspot.com",
        source_file_name='tmp.png',
        destination_blob_name=barcode,
    )

if __name__ == "__main__":
    uplode_mining(str(8801007099507))
