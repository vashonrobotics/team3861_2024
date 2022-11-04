package org.firstinspires.ftc.teamcode.subsystems;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


public class Vision {
    private final LinearOpMode robotOpMode;
    private static final String[] LABELS = {
            "image1",
            "image2",
            "image3"
    };

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private final Pose2d leftPosition1 = new Pose2d(-59, -12);
    private final Pose2d leftPosition2 = new Pose2d(-36, -12);
    private final Pose2d leftPosition3 = new Pose2d(-12, -12);

    private final Pose2d rightPosition1 = new Pose2d(59, -12);
    private final Pose2d rightPosition2 = new Pose2d(36, -12);
    private final Pose2d rightPosition3 = new Pose2d(12, -12);


    public Vision (LinearOpMode opMode) {
        robotOpMode = opMode;
    }

    public void init () {
        final String VUFORIA_KEY =
                "ATX87s7/////AAABmdP75JTOE0ShgAJ1CnEf4ahMNo1qgzeM+u53RnfxoGWfzuBp09/Lua+76wJ4nngpzRbp2O08gMzvknONJiIRBZP7JXlZx87u0NFL1KsSVPAYyHYnMknl4BzgCvRJ5gLzwd8NKcd5PLdUd1eEKRfikRr087Vds6hAlu7YRF9OmmhO2Hi0e1UmOhnWysg0CktFRTITaCW6brYVC1IfHl8R9GB0xgdlcuNztOm0LtOCf48DCsaetn2hlhC7mjWt15CjNFyK7w47M0OovFoghxhf+vtKMxqcyzqQPu3/OqqgPpGboeiNnLvbaChcQ7gCg7W2skUp5/BTZJMqO0oyQceolzUuCrdhf7n+D1dD4h6WPxyS";

        final String TFOD_MODEL_ASSET = "PowerPlay.tflite";

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = robotOpMode.hardwareMap.get(WebcamName.class, "webcam");

        vuforia = ClassFactory.getInstance()
                              .createVuforia(parameters);

        int tfodMonitorViewId = robotOpMode.hardwareMap.appContext.getResources()
                                                                  .getIdentifier(
                                                                          "tfodMonitorViewId", "id", robotOpMode.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance()
                           .createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.23, 16.0 / 9.0);
        }

    }

    public Pose2d getLeftFinalPosition () {
        return leftPosition2;
    }

    public Pose2d getRightFinalPosition () {
        return rightPosition2;
    }
}






    /*
    private Pose2d getPosition(Recognition rec) {

        if (rec.getLabel() == "image1") {
            return position1;
        }

        else if (rec.getLabel() == "image2") {
            return position2;
        }

        else if (rec.getLabel() == "image3" ) {
            return position3;
        }

        else {
            return position2;
        }

    }
    */

        /*
        if (tfod == null) {
            return position2;
        }

        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

        if (updatedRecognitions.size() == 0) {
            return position2;
        }

        if (updatedRecognitions.size() == 1) {
            return getPosition(updatedRecognitions.get(0));
        }

        return null;

    }
    */



     /*
        if (updatedRecognitions.size() > 1) {

            double maxConfidence = 0.0;
            Recognition rec = null;

            for (Recognition recognition : updatedRecognitions) {
                if (recognition.getConfidence() > maxConfidence) {
                    maxConfidence = recognition.getConfidence();
                    rec = recognition;
                }
            }

            return getPosition(rec);
        }
        */

