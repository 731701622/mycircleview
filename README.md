# 安卓作业

##自定义圆形头像  

主要就是去继承AppCompatImageView类，然后重写onMature方法，保持view的长宽一致。之后写个getscale类用于缩放图片到合适的大小，因为图片的大小与我们控件的大小可能过大或者过小，要通过缩放图片的大小去匹配我们的控件。然后用BitmapShader在canvas上绘制。  
这里说一下BitmapShader：BitmapShader是Shader的子类，可以通过Paint.setShader（Shader shader）进行设置，构造方法：
    `mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);`
TileMode的取值有三种：CLAMP 拉伸，REPEAT 重复 ，MIRROR 镜像  
具体意思差不多是BitmapShader通过用mPaint根据设置的TileMode绘制区域进行着色绘图。然后注意BitmapShader是从你的画布的左上角开始绘制的，不会在你view的左上角开始。

ps：本周考试周，没花太多的时间来写界面比如item点击和整体美观性。考试使我头秃。。。。。
