

package org.firstinspires.ftc.teamcode;


import java.util.List;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;


@Disabled
@TeleOp(name = "Concept: TensorFlow Object Detection Webcam", group = "Concept")
public class VisionTest extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";

    private static final String[] LABELS = {
            "image1",
            "image2",
            "image3"
    };

    private static final String VUFORIA_KEY =
            "ATX87s7/////AAABmdP75JTOE0ShgAJ1CnEf4ahMNo1qgzeM+u53RnfxoGWfzuBp09/Lua+76wJ4nngpzRbp2O08gMzvknONJiIRBZP7JXlZx87u0NFL1KsSVPAYyHYnMknl4BzgCvRJ5gLzwd8NKcd5PLdUd1eEKRfikRr087Vds6hAlu7YRF9OmmhO2Hi0e1UmOhnWysg0CktFRTITaCW6brYVC1IfHl8R9GB0xgdlcuNztOm0LtOCf48DCsaetn2hlhC7mjWt15CjNFyK7w47M0OovFoghxhf+vtKMxqcyzqQPu3/OqqgPpGboeiNnLvbaChcQ7gCg7W2skUp5/BTZJMqO0oyQceolzUuCrdhf7n+D1dD4h6WPxyS";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;


    public void runOpMode() {
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.23, 16.0/9.0);
        }


        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {

                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Objects Detected", updatedRecognitions.size());


                        for (Recognition recognition : updatedRecognitions) {
                            double col = (recognition.getLeft() + recognition.getRight()) / 2 ;
                            double row = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                            double width  = Math.abs(recognition.getRight() - recognition.getLeft()) ;
                            double height = Math.abs(recognition.getTop()  - recognition.getBottom()) ;

                            telemetry.addData(""," ");
                            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100 );
                            telemetry.addData("- Position (Row/Col)","%.0f / %.0f", row, col);
                            telemetry.addData("- Size (Width/Height)","%.0f / %.0f", width, height);
                        }
                        telemetry.update();
                    }
                }
            }
        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "webcam");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }


    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        // tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }
}




