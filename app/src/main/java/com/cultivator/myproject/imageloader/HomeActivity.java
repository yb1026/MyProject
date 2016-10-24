/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.cultivator.myproject.imageloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.cultivator.myproject.R;
import com.cultivator.myproject.imageloader.Constants.Extra;
import com.nostra13.universalimageloader.cache.disc.impl.FileCountLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.L;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class HomeActivity extends BaseActivity {

	private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uil_ac_home);

		// 定义文件对象，目录：/mnt/sdcard, 文件名:TEST_FILE_NAME
		File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
		if (!testImageOnSdCard.exists()) {	// 如果文件不存在
			// 把文件复制到SD卡
			copyTestImageToSdCard(testImageOnSdCard);
		}
	}

	// 点击进入ListView展示界面
	public void onImageListClick(View view) {
		Intent intent = new Intent(this, ImageListActivity.class);
		intent.putExtra(Extra.IMAGES, Constants.IMAGES);
		startActivity(intent);
	}

	// 点击进入GridView展示界面
	public void onImageGridClick(View view) {
		Intent intent = new Intent(this, ImageGridActivity.class);
		intent.putExtra(Extra.IMAGES,  Constants.IMAGES);
		startActivity(intent);
	}

	// 点击进入ViewPager展示界面
	public void onImagePagerClick(View view) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra(Extra.IMAGES,  Constants.IMAGES);
		startActivity(intent);
	}

	// 点击进入画廊展示界面
	public void onImageGalleryClick(View view) {
		Intent intent = new Intent(this, ImageGalleryActivity.class);
		intent.putExtra(Extra.IMAGES,  Constants.IMAGES);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		imageLoader.stop();		// 停止加载图片
		super.onBackPressed();
	}

	/**
	 * 开一个线程把assert目录下的图片复制到SD卡目录下
	 * @param testImageOnSdCard
	 */
	private void copyTestImageToSdCard(final File testImageOnSdCard) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream is = getAssets().open(TEST_FILE_NAME);
					FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
					byte[] buffer = new byte[8192];
					int read;
					try {
						while ((read = is.read(buffer)) != -1) {
							fos.write(buffer, 0, read);	// 写入输出流
						}
					} finally {
						fos.flush();		// 写入SD卡
						fos.close();		// 关闭输出流
						is.close();			// 关闭输入流
					}
				} catch (IOException e) {
					L.w("Can't copy test image onto SD card");
				}
			}
		}).start();		// 启动线程
	}
	
	
	
	
	private void init(){
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			// 设置图片在下载期间显示的图片
			.showStubImage(R.mipmap.ic_launcher)//
			// 设置图片Uri为空或是错误的时候显示的图片
			.showImageForEmptyUri(R.mipmap.ic_launcher)//
			// 设置图片加载/解码过程中错误时候显示的图片
			.showImageOnFail(R.mipmap.ic_launcher)//
			// 设置图片在下载前是否重置，复位
			.resetViewBeforeLoading()//
			// 设置下载的图片是否缓存在内存中
			.cacheInMemory()//
			// 设置下载的图片是否缓存在SD卡中
			.cacheOnDisc()//
			// 设置图片的解码类型,默认值——Bitmap.Config.ARGB_8888
			.bitmapConfig(Bitmap.Config.RGB_565)
			/**
			 *  设置图片的解码配置 android.graphics.BitmapFactory.Options
			 *  注意:选择inSampleSize将不考虑的选项
			 * 会根据imageScaleType(imageScaleType)选项设置大小
			 *  注意:这个选项重叠bitmapConfig()选项。
			 */
			//.decodingOptions(decodingOptions)
			// 设置图片下载前的延迟
			 //.delayBeforeLoading( delayInMillis)
			/**
			 *  设置图片加入缓存前，对bitmap进行设置 BitmapProcessor preProcessor
			 *  设置位图处理器将位图过程之前,他们会在内存中缓存。所以内存缓存将包含位图处理传入的预处理器。
			 */
			//	.preProcessor((BitmapProcessor)null)
			// 设置显示前的图片，显示后这个图片一直保留在缓存中
			// .postProcessor(BitmapProcessor postProcessor)
				/**
				 * 设置图片以如何的编码方式显示 imageScaleType(ImageScaleType imageScaleType)
				 * EXACTLY :图像将完全按比例缩小的目标大小
				 * EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数倍
				 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
				 *  NONE:图片不会调整
				 */
			// .imageScaleType( imageScaleType)
			/**
			 * 设置图片的显示方式 默认值-
			 * DefaultConfigurationFactory.createBitmapDisplayer()
			 * 
			 * @param displayer
			 *            RoundedBitmapDisplayer（int roundPixels）设置圆角图片
			 *            FakeBitmapDisplayer（）这个类什么都没做
			 *            FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
			 *             SimpleBitmapDisplayer()正常显示一张图片　
			 */


			.displayer(new RoundedBitmapDisplayer(20))//
			.build();


		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)


		/**
		 * 你可以设置你自己实现的内存缓存
		 */
		.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
     	/**
     	 * 为位图最大内存缓存大小(以字节为单位),默认值,可用应用程序内存的1/8
     	 * 注意:如果你使用这个方法,那么LruMemoryCache将被用作内存缓存。
     	 * 您可以使用memoryCache(MemoryCacheAware)方法来设置自己的MemoryCacheAware的实现。	
     	 */
		.memoryCacheSize(2 * 1024 * 1024)
		
		/**
		 * 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
		 */
		.denyCacheImageMultipleSizesInMemory()
		
		/**
		 * 设置本地图片缓存 也可以设置你自己实现 盘缓存必需实现 DiscCacheAware接口
		 * 类型（在com.nostra13.universalimageloader.cache.disc.impl包下能找到如下的类）：
		 * FileCountLimitedDiscCache(File cacheDir, int maxFileCount):设置缓存路径和缓存文件的数量，超过数量后，old将被删除
		 * 
		 * FileCountLimitedDiscCache(File cacheDir,FileNameGenerator fileNameGenerator,int maxFileCount):第二个参数是通过图片的url生成的唯一文件名。
		 * 
		 * LimitedAgeDiscCache(File cacheDir, FileNameGenerator fileNameGenerator, long maxAge) :第二个参数同上
		 * 
		 * LimitedAgeDiscCache(File cacheDir, long maxAge):maxAge为定义的时间，超过时间后，图片将被删除
		 * 
		 * TotalSizeLimitedDiscCache(File cacheDir, FileNameGenerator fileNameGenerator, int maxCacheSize) :第二个参数同上
		 * 
		 * TotalSizeLimitedDiscCache(File cacheDir, int maxCacheSize) :定义缓存的大小，如超过了，就会删除old图片。 UnlimitedDiscCache(File cacheDir) ：缓存没有限制
		 * 
		 * UnlimitedDiscCache(File cacheDir, FileNameGenerator fileNameGenerator)：第二个参数同上
		 */
		.discCache(new FileCountLimitedDiscCache(new File("/sdcard/cache"), 100))//
		/**
		 * 设置缓存的大小(以字节为单位)默认:本地缓存是不限制大小
		 * 注意:如果你使用这个方法,那么TotalSizeLimitedDiscCache将被用作磁盘缓存
		 * 您可以使用discCache(DiscCacheAware)DiscCacheAware引入自己的实现方法
		 * 
		 * @param maxCacheSize大小
		 */
		.discCacheSize(10*1024*1024)
		/**
	     * 设置图片保存到本地的参数
	     * @param maxImageWidthForDiscCache 保存的最大宽度
	     * @param maxImageHeightForDiscCache 保存的最大高度
	     * @param compressFormat    保存的压缩格式
	     * @param compressQuality 提示压缩的程度，有0-100.想png这种图片无损耗，就不必设置了
	     * @param BitmapProcessor 处理位图,可以更改原来的位图,实现必须是线程安全的。
	     */
	   .discCacheExtraOptions(100,10, Bitmap.CompressFormat.JPEG,0 )
		/**
		 * 设置缓存文件的数量
		 * @param maxFileCount数量
		 */
		.discCacheFileCount(100)
		/**
		 * .taskExecutor(Executor executor) 添加个线程池，进行下载
		 * 
		 * @param executor
		 *            线程池
		 *            如果进行了这个设置，那么threadPoolSize(int)，threadPriority(
		 *            int)，tasksProcessingOrder(QueueProcessingType)
		 *            将不会起作用
		 */
		
		/**
		 * 设置缓存文件的名字
		 * 
		 * @param fileNameGenerator
		 *            discCacheFileNameGenerator(FileNameGenerator
		 *            fileNameGenerator) 参数fileNameGenerator：
		 *            HashCodeFileNameGenerator
		 *            ()：通过HashCode将url生成文件的唯一名字
		 *            Md5FileNameGenerator()：通过Md5将url生产文件的唯一名字
		 */
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		
		/**
		 * 设置用于显示图片的线程池大小
		 * @param threadPoolSize
		 */
		.threadPoolSize(5)//
		
		
		/**
		 * 设置线程的优先级
		 * @param threadPriority
		 */
		.threadPriority(Thread.MIN_PRIORITY + 3)
		/**
		 * tasksProcessingOrder(QueueProcessingType tasksProcessingType)
		 * 设置图片下载和显示的工作队列排序
		 * 
		 * @param tasksProcessingType
		 */
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		/**
		 * taskExecutorForCachedImages(Executor executorForCachedImages)
		 * 下载缓存图片
		 * 
		 * @param executorForCachedImages
		 */
				// =========================================================//
		.build();


		ImageLoader.getInstance().init(config);
				 
			 
	}
	
	
}