package org.firstinspires.ftc.teamcode.subsystems;

import android.content.Context;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class Vision {

    private final LinearOpMode robotOpMode;

    private TFObjectDetector tfod;

    private String detectedImage = null;

    ArrayList<Pose2d> leftPose = new ArrayList<>();
    ArrayList<Pose2d> rightPose = new ArrayList<>();

    HashMap<String, ArrayList<Pose2d>> finalPositions = new HashMap<>();

    public Vision (LinearOpMode opMode) {
        robotOpMode = opMode;
    }

    public boolean isImageDetected () {
        return detectedImage != null;
    }

    public String getDetectedImage () {
        return detectedImage;
    }

    public void init () {

        final Context appContext = robotOpMode.hardwareMap.appContext;

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey =
                "ATX87s7/////AAABmdP75JTOE0ShgAJ1CnEf4ahMNo1qgzeM+u53RnfxoGWfzuBp09/Lua+76wJ4" +
                "nngpzRbp2O08gMzvknONJiIRBZP7JXlZx87u0NFL1KsSVPAYyHYnMknl4BzgCvRJ5gLzwd8NKcd5" +
                "PLdUd1eEKRfikRr087Vds6hAlu7YRF9OmmhO2Hi0e1UmOhnWysg0CktFRTITaCW6brYVC1IfHl8R" +
                "9GB0xgdlcuNztOm0LtOCf48DCsaetn2hlhC7mjWt15CjNFyK7w47M0OovFoghxhf+vtKMxqcyzqQ" +
                "Pu3/OqqgPpGboeiNnLvbaChcQ7gCg7W2skUp5/BTZJMqO0oyQceolzUuCrdhf7n+D1dD4h6WPxyS";

        parameters.cameraName = robotOpMode.hardwareMap.get(WebcamName.class, "webcam");

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(parameters);

        int tfodMonitorViewId = appContext.getResources().getIdentifier(
                "tfodMonitorViewId",
                "id",
                appContext.getPackageName()
        );

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        tfodParameters.minResultConfidence = 0.80f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 640;
        tfodParameters.maxNumDetections = 1;

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        tfod.loadModelFromAsset("Power.tflite", "image1", "image2", "image3");

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1, 16.0 / 9.0);
        }

        // default positions for both left and right sides

        leftPose.add(new Pose2d(-57, -11, Math.toRadians(0)));     // Left Position 1
        leftPose.add(new Pose2d(-36, -11, Math.toRadians(0)));     // Left Position 2
        leftPose.add(new Pose2d(-12, -11, Math.toRadians(0)));     // Left Postionn 3

        rightPose.add(new Pose2d(12, -11, Math.toRadians(180)));   // Right Position 1
        rightPose.add(new Pose2d(36, -13, Math.toRadians(180)));   // Right Position 2
        rightPose.add(new Pose2d(55, -10, Math.toRadians(180)));   // Right Postionn 3

        finalPositions.put("right", rightPose);
        finalPositions.put("left", leftPose);
    }




    public Pose2d detectFinalPosition (String side) throws AssertionError {
        long runTime = System.currentTimeMillis();

        ArrayList<Pose2d> finalPose = finalPositions.get(side);

        if (finalPose == null) throw new AssertionError();

        // set default to Position 2 in case we can't detect the image
        // we have a 1 in 3 chance of being correct

        Pose2d finalPosition = finalPose.get(1);

        // loop for 1.5 seconds

        while (System.currentTimeMillis() - runTime < 1500) {

            // break out of while loop if OpMode is stopped

            if (robotOpMode.isStopRequested()) {
                break;
            }

            // break out of while loop if image is detected

            if (isImageDetected()) {
                break;
            }

            // continue loop until tfod object is ready

            if (tfod == null) {
                continue;
            }

            // get list of recognized objects

            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

            // continue loop if no objects are detected

            if (updatedRecognitions == null) {
                continue;
            }

            // loop over each detected object

            for (Recognition object : updatedRecognitions) {
                String label = object.getLabel();

                if (label.equals("image1")) {
                    finalPosition = finalPose.get(0);
                    detectedImage = "image1";
                }

                if (label.equals("image2")) {
                    finalPosition = finalPose.get(1);
                    detectedImage = "image2";
                }

                if (label.equals("image3")) {
                    finalPosition = finalPose.get(2);
                    detectedImage = "image3";
                }
            }
        }

        // return the final position

        return finalPosition;
    }

}

