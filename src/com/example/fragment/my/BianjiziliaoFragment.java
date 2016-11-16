package com.example.fragment.my;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.avos.avoscloud.AVUser;
import com.example.UIhelper.MtitlePopupWindow;
import com.example.UIhelper.MtitlePopupWindow.OnPopupWindowClickListener;
import com.example.fragment.BasicFragment;
import com.example.model.LeanUser;
import com.example.uiwork.R;
import com.example.utils.PathUtils;
import com.squareup.picasso.Picasso;

public class BianjiziliaoFragment extends BasicFragment{

	private RelativeLayout personal_touxiang;
	private RelativeLayout personal_name;
	private RelativeLayout personal_sex;
	private RelativeLayout personal_diqu;
	private RelativeLayout personal_intro;
	
	private static final int NONE = 0;
	private static final int PHOTO_GRAPH = 1;// 拍照
	private static final int PHOTO_ZOOM = 2; // 相册
	private static final int PHOTO_RESOULT = 3;// 结果
	private static final String IMAGE_UNSPECIFIED = "image/*";
	
	private ImageView image;
	private TextView nameTextView;
	private TextView sexTextView;
	private TextView diquTextView;
	private TextView introTextView;
	
	MtitlePopupWindow ntitlePopupWindow;
	String text2 = "请选择地区";
	String[] items1 = {"北京市","上海市","天津市","重庆市","成都市","武汉市","西安市",
			"广州市","深圳市","郑州市","杭州市","沈阳市","南京市","长沙市","昆明市",
			"青岛市","合肥市","济南市","福州市","太原市"};
	
	public BianjiziliaoFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bianjiziliao,
				container, false);
		
		image = (ImageView) rootView.findViewById(R.id.ziliao_photo);
		nameTextView = (TextView) rootView.findViewById(R.id.personal_username);
		sexTextView = (TextView) rootView.findViewById(R.id.personal_usersex);
		diquTextView =(TextView) rootView.findViewById(R.id.personal_usercity);
		introTextView = (TextView) rootView.findViewById(R.id.personal_userintroduction);
		
		LeanUser currentUser = LeanUser.getCurrentUser();

		//头像
		if(currentUser.getAvatarUrl() == null){
			Picasso.with(getActivity()).load(R.drawable.article_moren).into(image);
		}else{
		Picasso.with(getActivity()).load(currentUser.getAvatarUrl()).into(image);
		}
		//名字
		nameTextView.setText(currentUser.getUsername());
		//性别
		Boolean sexNumber = currentUser.getBoolean("sex");
		
		if(sexNumber == true){
			sexTextView.setText("女");
		}else if(sexNumber == false){
			sexTextView.setText("男");
		}
		//地区
		diquTextView.setText(currentUser.getString("city"));
		//简介
		introTextView.setText(currentUser.getString("brief_introduce"));
		
		return rootView;
	}
	
	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) 
	{
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("编辑资料");
		
		/*
		 * 更改头像
		 */
		personal_touxiang = (RelativeLayout)v.findViewById(R.id.personal_touxiang);
		personal_touxiang.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				dialog.setContentView(R.layout.dialog_avater_picker);
				dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new OnClickListener() {

					//拍照
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"temp.jpg")));
						startActivityForResult(intent, PHOTO_GRAPH);
						dialog.dismiss();
					}
				});
				
				dialog.findViewById(R.id.tv_pick_photo).setOnClickListener(
						new OnClickListener() {
							
							//从相册中选择
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(Intent.ACTION_PICK, null);
								intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
								startActivityForResult(intent, PHOTO_ZOOM);
								dialog.dismiss();
							}
								
				});
				dialog.findViewById(R.id.btn_quxiao).setOnClickListener(
						new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
				});
				
				dialog.show();

			}
		});
		
		/*
		 * 编辑昵称
		 */
		personal_name = (RelativeLayout)v.findViewById(R.id.personal_name);        
		personal_name.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				//弹出编辑框
				 final EditText inputnameServer = new EditText(getActivity());
			        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			        builder.setTitle("请输入昵称").setView(inputnameServer)
			                .setNegativeButton("取消", null);
			        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			            public void onClick(DialogInterface dialog, int which) {
			               String newname = inputnameServer.getText().toString();
			               nameTextView.setText(newname);
			               
			               //保存到数据库
			               AVUser user = AVUser.getCurrentUser();
			               user.put("username", newname);
			               user.saveInBackground();
			             }
			        });
			        builder.show();
			}
			
		});
		
		/*
		 * 编辑性别
		 */
		personal_sex = (RelativeLayout)v.findViewById(R.id.personal_sex);
		personal_sex.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请选择性别");
                final String[] sex = {"男", "女"};
                
                builder.setSingleChoiceItems(sex, 0, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(getActivity(),"你选择的性别是：" + sex[which], Toast.LENGTH_SHORT).show();
                        AVUser user = AVUser.getCurrentUser();
                    	System.out.println("which:" + which);
                    	if(which == 0){
                    		System.out.println("which:" + which);
                    		sexTextView.setText(sex[0]);
	                		user.put("sex",false);
	                		user.saveInBackground();
                    	}
                    	else if (which == 1){
                    		System.out.println("which:" + which);
                    		sexTextView.setText(sex[1]);
                    		user.put("sex",true);
                    		user.saveInBackground();
                    	}
                    	dialog.dismiss();
                    }
                });
                builder.show();
            }
		});
		
		/*
		 * 选择地区
		 */
		personal_diqu = (RelativeLayout) v.findViewById(R.id.personal_city);
		
		personal_diqu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ntitlePopupWindow.showAtLocation(v.findViewById(R.id.personal_usercity), 
						Gravity.BOTTOM, 0, 0);
				//设置背景颜色
				backgroundAlpha(0.7f);
			}
		});
		
		/*
		 * 用户简介
		 */
		personal_intro = (RelativeLayout) v.findViewById(R.id.personal_introduction);
		personal_intro.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//弹出编辑框
			  final EditText inputServer = new EditText(getActivity());
		        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		        builder.setTitle("请输入内容").setView(inputServer)
		                .setNegativeButton("取消", null);
		        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

		            public void onClick(DialogInterface dialog, int which) {
		               String context = inputServer.getText().toString();
		               introTextView.setText(context);
		               
		               //保存到数据库
		               AVUser user = AVUser.getCurrentUser();
		               user.put("brief_introduce", context);
		               user.saveInBackground();
		             }
		        });
		        builder.show();
			}
			
		});

		ntitlePopupWindow = new MtitlePopupWindow(getActivity());
		ntitlePopupWindow.changeData(text2, Arrays.asList(items1));
		ntitlePopupWindow.setOnPopupWindowClickListener(new OnPopupWindowClickListener() {
			
			@Override
			public void onPopupWindowItemClick(int position) {
				Toast.makeText(getActivity().getApplication(), "您选择了"+items1[position], Toast.LENGTH_SHORT).show();
				backgroundAlpha(1f);
				diquTextView.setText(items1[position]);	
				
				AVUser user = AVUser.getCurrentUser();
				user.put("city", items1[position]);
				user.saveInBackground();
				}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTO_GRAPH) {
		// 设置文件保存路径
			File picture = new File(Environment.getExternalStorageDirectory()+ "/temp.jpg");
			startPhotoZoom(Uri.fromFile(picture));
		}
		if (data == null)
			return;
	
		// 读取相册缩放图片
		if (requestCode == PHOTO_ZOOM) {
			startPhotoZoom(data.getData());
	      } 
		
		// 处理结果
		if (requestCode == PHOTO_RESOULT) {
			Bundle extras = data.getExtras();

		     if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
		
				//头像变成圆角显示
			     //Bitmap output=toRoundCorner(photo, 15.0f);
			     image.setImageBitmap(photo);
			     
			    //保存到数据库
			      final String path = saveCropAvatar(data);
			      LeanUser user = LeanUser.getCurrentUser();
			      user.saveAvatar(path, null);

		    
	      }
	}
	super.onActivityResult(requestCode, resultCode, data);
	}


	  private String saveCropAvatar(Intent data) {
	    Bundle extras = data.getExtras();
	    String path = null;
	    if (extras != null) {
	      Bitmap bitmap = extras.getParcelable("data");
	      if (bitmap != null) {
	        path = PathUtils.getAvatarCropPath();
	        saveBitmap(path, bitmap);
	        if (bitmap != null && bitmap.isRecycled() == false) {
	          bitmap.recycle();
	        }
	      }
	    }
	    return path;
	  }

	  public static void saveBitmap(String filePath, Bitmap bitmap) {
	    File file = new File(filePath);
	    if (!file.getParentFile().exists()) {
	      file.getParentFile().mkdirs();
	    }
	    FileOutputStream out = null;
	    try {
	      out = new FileOutputStream(file);
	      if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
	        out.flush();
	      }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        out.close();
	      } catch (Exception e) {
	      }
	    }
	  }

	/**
	* 收缩图片
	*
	* @param uri
	*/

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");//进行修剪
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 500);
		intent.putExtra("outputY", 500);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_RESOULT);
	}
	
	/**
	 * 变成圆角
	 * 
	 * @param bitmap,pixels
	 */
	/*public Bitmap toRoundCorner(Bitmap bitmap,float pixels) {
		int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
	    float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
	    if (width <= height) {
	       roundPx = width / 2 -5;
           top = 0;
           bottom = width;
           left = 0;
           right = width;
           height = width;
           dst_left = 0;
           dst_top = 0;
           dst_right = width;
           dst_bottom = width;
	       }else{
	    	   roundPx = height / 2 -5;
	           float clip = (width - height) / 2;
	           left = clip;
	           right = width - clip;
	           top = 0;
	           bottom = height;
	           width = height;
	           dst_left = 0;
	           dst_top = 0;
	           dst_right = height;
	           dst_bottom = height;
	           }
	    Bitmap output = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

       final int color = 0xff424242;
       final Paint paint = new Paint();
       final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
       final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
       final RectF rectF = new RectF(dst_left+15, dst_top+15, dst_right-20, dst_bottom-20);

       paint.setAntiAlias(true);
 
       canvas.drawARGB(0, 0, 0, 0);
       paint.setColor(color);
 
       canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
 
       paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
       canvas.drawBitmap(bitmap, src, dst, paint);
       return output;
       }*/
	
	public void backgroundAlpha(float bgAlpha)
	{
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}
