# TimePicker
time picker
自定义时间选择器
年月日 时 分  不包括秒
回掉特定格式时间串
show
![](https://github.com/caixingcun/picture/blob/master/timepick.gif)  


How to

To get a Git project into your build:

project
  build.gradle
  
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
module  
  build.gradle
  
 	dependencies {
	        compile 'com.github.caixingcun:TimePicker:1.0'
	}
  
  
use
      		  TimePickDialog dialog = new TimePickDialog(mContext, "开始时间");
             		   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           		     dialog.show();
             		   dialog.setTimePickListener(new TimePickDialog.TimePickListener() {
            		        @Override
              		      public void choseTime(String time) {
              		        //todo
              		      }
              		  });
