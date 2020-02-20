# MindevPDFViewer
[![](https://jitpack.io/v/mkw8263/MindevPDFViewer.svg)](https://jitpack.io/#mkw8263/MindevPDFViewer)

This project was created using a [PDFRender](https://developer.android.com/reference/android/graphics/pdf/PdfRenderer)<br>
The key to this technology is not thread safe class<br>
So I proceeded with the io using `Coroutine`<br>

### demo

![gif](https://github.com/mkw8263/MindevPDFViewer/blob/master/demo1.gif)

### apply in project
1.  Add `INTERNET` permissions on your AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

2. Add the dependency
```gradle
dependencies {
	   implementation 'com.github.mkw8263:MindevPDFViewer:1.0.1'
}
```

3. sample code
- Xml
```xml
    <com.mindev.mindev_pdfviewer.MindevPDFViewer
        android:id="@+id/pdf"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
<br>

- Code
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://gahp.net/wp-content/uploads/2017/09/sample.pdf"
        pdf.initializePDFDownloader(url, statusListener)
        lifecycle.addObserver(PdfScope())
    }

    private val statusListener = object : MindevPDFViewer.MindevViewerStatusListener {
        override fun onStartDownload() {
        }

        override fun onPageChanged(position: Int, total: Int) {
        }

        override fun onProgressDownload(currentStatus: Int) {
        }

        override fun onSuccessDownLoad(path: String) {
            pdf.fileInit(path)
        }

        override fun onFail(error: Throwable) {
        }

        override fun unsupportedDevice() {
        }

    }
}
```
### used lib
[subsampling-scale-image-view](https://github.com/davemorrissey/subsampling-scale-image-view)
