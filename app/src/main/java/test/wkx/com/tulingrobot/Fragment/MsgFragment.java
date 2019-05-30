package test.wkx.com.tulingrobot.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.wkx.com.tulingrobot.Activity.BaseApplication;
import test.wkx.com.tulingrobot.Bean.Msg;
import test.wkx.com.tulingrobot.Adapter.MsgAdapter;
import test.wkx.com.tulingrobot.R;

public class MsgFragment extends BaseFragment
{
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button sendRequest;
    private RecyclerView msgRecyclerView;
    private MsgAdapter msgAdapter ;
    private int position;
    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_msg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        msgRecyclerView = (RecyclerView) view.findViewById(R.id.rv_msg);
        msgRecyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
        msgRecyclerView.setAdapter(msgAdapter);
        inputText = (EditText)view.findViewById(R.id.input_text);
        sendRequest = (Button)view.findViewById(R.id.send_request);
        msgRecyclerView = (RecyclerView)view.findViewById(R.id.rv_msg);
        msgAdapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(msgAdapter);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                String content_1 = content.replace(" ","");
                String content_2 = content_1.replace("\n","");
                String message = null;
                try{
                    message = java.net.URLEncoder.encode(content_2, "UTF-8");
                }catch (Exception e){
                    e.printStackTrace();
                }
                String contentUrl = "http://www.tuling123.com/openapi/api?key=d2b1eedeaee7415ab61bfbc0b6865a3e&info=" + message;
                final String Url =contentUrl;
                if (!"".equals(content)){
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    msgAdapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                }
                final Handler mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 0:
                                msgAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        BufferedReader reader = null;
                        try{
                            URL url = new URL(Url);
                            connection = (HttpURLConnection)url.openConnection();
                            InputStream in = connection.getInputStream();
                            reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null){
                                response.append(line);
                            }
                            Log.d("response", response.toString());
                            String str = response.toString();
                            String pattern = "\"(.*?)\"";
                            Pattern r = Pattern.compile(pattern);
                            Matcher m = r.matcher(str);
                            int i=0;
                            while (m.find()){
                                str = m.group(1);
                            }
                            Msg msg1 = new Msg(str, Msg.TYPE_RECEIVED);
                            msgList.add(msg1);
                            mHandler.sendEmptyMessage(0);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            if (reader != null){
                                try {
                                    reader.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                            if (connection != null){
                                connection.disconnect();
                            }
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    protected void initData()
    {
        Msg msg1 = new Msg("您好！这里是图灵机器人，请问您有什么问题需要我帮助吗？", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
    }
}
