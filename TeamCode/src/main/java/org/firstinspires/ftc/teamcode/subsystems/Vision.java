package org.firstinspires.ftc.teamcode.subsystems;

import android.content.Context;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({ "unused"})

public class Vision {
    private final LinearOpMode robotOpMode;
    private TFObjectDetector tfod;
    private String detectedImage = null;


    public Vision (LinearOpMode opMode) {
        robotOpMode = opMode;
    }

    public boolean isImageDetected () {
        return detectedImage != null;
    }

    public String getDetectedImage () {
        if (detectedImage == null) {
            return "None: (default)";
        }

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
        tfodParameters.inputSize = 320;
        tfodParameters.maxNumDetections = 1;

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        tfod.loadModelFromAsset("Power-v3.tflite", "image1", "image2", "image3");

        if (tfod != null) {
            tfod.activate();
            tfod.setClippingMargins(215,0,80,0);
            tfod.setZoom(1.0, 16.0 / 9.0);
        }
    }



    public Pose2d detectFinalPosePosition (ArrayList<Pose2d> finalPosePositions) {
        long runTime = System.currentTimeMillis();

        // set default to Position 2 in case we can't detect the image
        // we have a 1 in 3 chance of being correct

        Pose2d finalPosePosition = finalPosePositions.get(1);

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
                    finalPosePosition = finalPosePositions.get(0);
                    detectedImage = "image1";
                }

                if (label.equals("image2")) {
                    finalPosePosition = finalPosePositions.get(1);
                    detectedImage = "image2";
                }

                if (label.equals("image3")) {
                    finalPosePosition = finalPosePositions.get(2);
                    detectedImage = "image3";
                }
            }
        }

        // return the final position

        return finalPosePosition;
    }

    public Integer detectFinalStrafePosition (ArrayList<Integer> finalStrafePositions) {
        long runTime = System.currentTimeMillis();

        // set default to Position 2 in case we can't detect the image
        // we have a 1 in 3 chance of being correct

        Integer finalStrafePosition = finalStrafePositions.get(1);

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
                    finalStrafePosition = finalStrafePositions.get(0);
                    detectedImage = "image1";
                }

                if (label.equals("image2")) {
                    finalStrafePosition = finalStrafePositions.get(1);
                    detectedImage = "image2";
                }

                if (label.equals("image3")) {
                    finalStrafePosition = finalStrafePositions.get(2);
                    detectedImage = "image3";
                }
            }
        }

        // return the final position

        return finalStrafePosition;
    }
}




