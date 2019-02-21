package kr.co.company.camera;
//여러 import들
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOError;
import java.io.IOException;
//클래스 정의
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    //프리뷰 함수
    public CameraPreview(Context context, Camera camera){
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    public void surfaceCreated(SurfaceHolder holder){
        try{//예외처리 함수
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        }catch (IOException e){
            Log.d("Camera", "Error setting camera preview: "+e.getMessage());
        }
    }
    public void surfaceDestroyed(SurfaceHolder holder){}
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}
}
