package org.firstinspires.ftc.teamcode.subsystems;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    private final Pose2d leftPosition1 = new Pose2d(-59, -12, Math.toRadians(0));
    private final Pose2d leftPosition2 = new Pose2d(-36, -12, Math.toRadians(0));
    private final Pose2d leftPosition3 = new Pose2d(-12, -12, Math.toRadians(0));

    private final Pose2d rightPosition1 = new Pose2d(12, -12, Math.toRadians(180));
    private final Pose2d rightPosition2 = new Pose2d(36, -12, Math.toRadians(180));
    private final Pose2d rightPosition3 = new Pose2d(59, -12, Math.toRadians(180));


    public Vision (LinearOpMode opMode) {
        robotOpMode = opMode;
    }

    public void init () {
        final String VUFORIA_KEY =
                "ATX87s7/////AAABmdP75JTOE0ShgAJ1CnEf4ahMNo1qgzeM+u53RnfxoGWfzuBp09/Lua+76wJ4nngpzRbp2O08gMzvknONJiIRBZP7JXlZx87u0NFL1KsSVPAYyHYnMknl4BzgCvRJ5gLzwd8NKcd5PLdUd1eEKRfikRr087Vds6hAlu7YRF9OmmhO2Hi0e1UmOhnWysg0CktFRTITaCW6brYVC1IfHl8R9GB0xgdlcuNztOm0LtOCf48DCsaetn2hlhC7mjWt15CjNFyK7w47M0OovFoghxhf+vtKMxqcyzqQPu3/OqqgPpGboeiNnLvbaChcQ7gCg7W2skUp5/BTZJMqO0oyQceolzUuCrdhf7n+D1dD4h6WPxyS";

        final String TFOD_MODEL_ASSET = "Power.tflite";

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
            tfod.setZoom(1, 16.0 / 9.0);
        }
    }


    private boolean isImage1(Recognition rec) {
        return rec.getLabel().equals(("image1"));
    }

    private boolean isImage2(Recognition rec) {
        return rec.getLabel().equals(("image2"));
    }

    private boolean isImage3(Recognition rec) {
        return rec.getLabel().equals(("image3"));
    }

    public Pose2d detectFinalPosition(String side) {
        Telemetry telemetry = robotOpMode.telemetry;
        boolean imageDetected = false;

        Pose2d position = leftPosition2;

        double loopCount = 0;

        while ( loopCount < 100 || !imageDetected ) {

            if (tfod == null) {
                telemetry.addData("detectFinalPosition", "Error: tfod is null");
                telemetry.update();
                continue;
            }

            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

            if (updatedRecognitions == null) {
                telemetry.addData("detectFinalPosition", "Error: updateRecognitions is null");
                telemetry.update();
                continue;
            }

            telemetry.addData("Objects Detected", updatedRecognitions.size());

            for (Recognition object : updatedRecognitions) {

                telemetry.addData(object.getLabel(), "   %.0f%%", object.getConfidence() * 100);
                telemetry.addData("finalPosition", object.getLabel());
                telemetry.update();

                if (isImage1(object)) {
                    position = (side.equals("left")) ? leftPosition1 : rightPosition1;
                    imageDetected = true;
                }

                if (isImage2(object)) {
                    position = (side.equals("left")) ? leftPosition2 : rightPosition2;
                    imageDetected = true;
                }

                if (isImage3(object)) {
                    position = (side.equals("left")) ? leftPosition3 : rightPosition3;
                    imageDetected = true;
                }
            }

            telemetry.update();

            loopCount++;
        }

        return position;
    }

    public Pose2d getRightFinalPosition() {
        return rightPosition2;
    }

}







/*



        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

        Recognition rec = updatedRecognitions.get(0);
        Pose2d position = leftPosition2;

        if (isImage1(rec)) {
            position = leftPosition1;
        }

        if (isImage2(rec)) {
            position = leftPosition2;
        }

        if (isImage3(rec)) {
            position = leftPosition3;
        }

        return position;


====================================
    private Pose2d getPosition(Recognition rec, String side) {

        if (rec.getLabel() == "image1") {
            return (side == "left") ? leftPosition1 : rightPosition1;
        }

        else if (rec.getLabel() == "image3" ) {
            return (side == "left") ? leftPosition3 : rightPosition3;
        }

        else {
            return (side == "left") ? leftPosition2 : rightPosition2;
        }

    }



    public Pose2d getLeftFinalPosition () {

        double maxConfidence = 0.0;
        Recognition detectedObject = null;

        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

        for (Recognition rec : updatedRecognitions) {
            if (rec.getConfidence() > maxConfidence) {
                maxConfidence = rec.getConfidence();
                detectedObject = rec;
            }
        }

        return (detectedObject == null)
                ? leftPosition2
                : getPosition(detectedObject, "left");
    }


    public Pose2d getRightFinalPosition () {
        return rightPosition2;
    }

*/

