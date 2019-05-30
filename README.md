# 安卓作业

##图灵机器人在线对话  

图灵机器人app主要采用了fragment+viewpager+tablayout+recycleview完成  
recycleview主要参照第一行代码，用Nine-patch图片作为发出消息的背景图，定义消息的实体类Msg，编写RecyclerView子项布局 msg_item.xml，创建RecyclerView的适配器MsgAdapter，使用HttpURLConnection，用getInputStream（）方法就可以获取到服务器返回的输入流


这里说一波这几天踩的坑：  
1.使用tablayout的时候，文字和图片添加之后，文字无法显示，采用了几种显示方式，最后发现不能将imageview的padding设的太大，文字其实被隐藏在屏幕的下端。  
2在使用viewpager+fragment时，fragment的onCreateView一定要return！！不然就是白板  
3.fragment的及时刷新问题：在与机器人对话时，机器人的消息不能及时返回，于是我采用 msgAdapter.notifyDataSetChanged()方法刷新，结果只能通过下拉或者点击的方式才能刷新，最后采用了Handle去拿信息： final Handler mHandler = new Handler() {
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
