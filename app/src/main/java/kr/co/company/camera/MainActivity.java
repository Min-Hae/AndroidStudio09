////////////////카메라로 사진찍는 앱////////////
package kr.co.company.camera;
//여러 import들
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


//클래스 정의
public class MainActivity extends AppCompatActivity {
    private Camera mCamera;
    private CameraPreview mPreview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //activity_main과 연결
        setContentView(R.layout.activity_main);
        //카메라 사용
        mCamera = getCameraInstance();
        mPreview = new CameraPreview(this, mCamera); //프리뷰 사용
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview); //프리뷰를 추가한다.
        //버튼 클릭 리스너 설정
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture); //버튼을 누르면 찍는다.
            }
        });
    }   //콜백 메소드
    private android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            File pictureFileDir = getDir();
            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                Log.d("Camera", "Can't create directory.");
                Toast.makeText(getApplicationContext(), "Can't create directory.", Toast.LENGTH_LONG).show();
                return;
            }//이름을 연도,날짜,시간으로 저장
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
            String date = dateFormat.format(new Date());
            String photoFile = "MyPic" + date + ".jpg";
            String filename = pictureFileDir.getPath() + File.separator + photoFile;

            File pictureFile = new File(filename);
            try {//예외 처리 함수
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                Toast.makeText(getApplicationContext(), "New Image is saved:" + photoFile, Toast.LENGTH_LONG).show();
            } catch (Exception error) {
                Log.d("Camera", "File" + filename + "Image was not saved: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Image was not saved.", Toast.LENGTH_LONG).show();
            }
        }
        //파일을 이용
        private File getDir() {
            File sdDir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            return new File(sdDir, "CameraCapture");
        }
    };
    public static Camera getCameraInstance(){
        Camera c = null;
        try {//예외 처리 함수
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }
}
