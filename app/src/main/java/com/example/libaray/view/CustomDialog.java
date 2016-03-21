package com.example.libaray.view;

import android.app.Dialog;
import android.content.Context;

/**
 * 要记得加上style
 * <style name="dialog"
 parent="@android:style/Theme.Holo.Light.Dialog.NoActionBar">
 <item name="android:background">@android:color/transparent</item>
 <item name="android:windowBackground">@android:color/transparent</item>
 <item name="android:backgroundDimEnabled">true</item>
 <item name="android:backgroundDimAmount">0.6</item>
 <item name="android:windowFrame">@null</item>
 <item name="android:windowIsFloating">true</item>
 <item name="android:windowIsTranslucent">true</item>
 </style>
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    /*private Context context;
    private String title;
    private String confirmButtonText;
    private String cacelButtonText;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }

    public CustomDialog(Context context, String title) {
        //要传入一个styles，否则顶部有段白色的
        super(context, R.style.dialog);
        this.context = context;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update, null);
        setContentView(view);

        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_update_content);
        ImageView tvConfirm = (ImageView) view.findViewById(R.id.dialog_update_confirm);
        ImageView tvCancel = (ImageView) view.findViewById(R.id.dialog_update_cancel);

        tvTitle.setText(title);

        tvConfirm.setOnClickListener(new clickListener());
        tvCancel.setOnClickListener(new clickListener());
        *//*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         *//*
        Window dialogWindow = getWindow();
         *//*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         * 
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         *//*
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        // 新位置X坐标
        //lp.x = 100;
        // 新位置Y坐标
        //lp.y = 100;
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.dialog_update_confirm:
                    clickListenerInterface.doConfirm();
                    break;
                case R.id.dialog_update_cancel:
                    clickListenerInterface.doCancel();
                    break;
            }
        }

    };*/

}