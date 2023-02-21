package com.example.rampasimage.ui.gallery;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HttpService {

    @Multipart
    @POST("upload_file/RestApi/multi_upload.php")
    Call<FileModel> callMultipleUploadApi(@Part List<MultipartBody.Part> image);
}
