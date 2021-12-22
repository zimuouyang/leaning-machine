package com.leaning_machine.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.application.GlideApp;
import com.leaning_machine.base.dto.AppDto;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.layout.CommonDialog;
import com.leaning_machine.model.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class InstallAppAdapter extends RecyclerView.Adapter<InstallAppAdapter.MyViewHolder> {
    private Context context;
    private List<AppDto> list;
    private View inflater;

    public InstallAppAdapter(Context context, List<AppDto> list) {
        this.context = context;
        this.list = list;
    }

    public InstallAppAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<AppDto> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_install_app, parent, false);
        return new InstallAppAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppDto app = list.get(position);
        GlideApp.with(context).load("http://8.142.131.31:8080/image/" + app.getApkIconFileId()).error(R.mipmap.top_avatar_1).into(holder.appImageView);
        holder.appNameText.setText(app.getAppName());
        holder.installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadApp();
            }
        });

        if (app.getReleaseNote() != null && !app.getReleaseNote().isEmpty()) {
            holder.appDesView.setVisibility(View.VISIBLE);
            holder.appDesView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonDialog.newBuilder().context(context).des(app.getReleaseNote()).build().show();
                }
            });
        } else {
            holder.appDesView.setVisibility(View.GONE);
        }
    }

    private void downloadApp() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("fileName", "2021/12/02/7f535f2d-2e22-49be-9b20-0e9ed3525659.apk")
                .build();
        Request request = new Request.Builder()
                .url("http://8.142.131.31:8080/api/v1/app/download")
                .method("POST", body)
                .addHeader("Authorization", "Terminal_Bearer eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNocyEEOhCAMBdC7_DUk7VAocBs0NDqryYiJifHuGpfvnWj7WFBP7Fv_o6JtzXA5fMf6aNJZCmf2psm8cCKfoxUfg-a5W2STAoe1DVROQkT6UXLox--NICk8cd0AAAD__w.meI2biRHZSyDxgPqod4a_8Ob7QNgCObsI5k4JXYkNBBERktVnd3bb3Wq6GSfqckYA7xmXPBi0Ecg01dFQBLhrg")
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("zzz", "success " + response.isSuccessful());
                    Log.d("zzz", response.headers().toString() + "   "+ response.body().contentLength());
//                    writeResponseBodyToDisk(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        Observable.create(new Observable.OnSubscribe<Response>() {
//            @Override
//            public void call(Subscriber<? super Response> subscriber) {
//
//                    CommonApiService.instance.
//                            downloadAppFile("2021/12/02/7f535f2d-2e22-49be-9b20-0e9ed3525659.apk").enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            boolean success = response.isSuccessful();
//                            Log.d("zzz", success + "   ");
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                        }
//                    });
//
//                    subscriber.onNext(null);
//
//                subscriber.onCompleted();
//            }
//
//        }).subscribeOn(Schedulers.io()).subscribe(new Action1<Response>() {
//            @Override
//            public void call(Response response) {
////                Log.d("zzz", response.isSuccessful() + "   " + response.headers().toString());
//            }
//        });


    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView appImageView;
        ImageView appDesView;
        TextView appNameText;
        Button installButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            appImageView = itemView.findViewById(R.id.app_img);
            appNameText = itemView.findViewById(R.id.app_name);
            appDesView = itemView.findViewById(R.id.app_des);
            installButton = itemView.findViewById(R.id.install_btn);
        }
    }

    public void openApp(String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            Toast.makeText(context, "未安装", Toast.LENGTH_LONG).show();
        } else {
            context.startActivity(intent);
        }
    }
}
