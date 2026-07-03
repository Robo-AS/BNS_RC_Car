package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;
import dev.frozenmilk.dairy.cachinghardware.CachingServo;


@Config
public class DriveTrain extends SubsystemBase {
    private static DriveTrain instance = null;

    public CachingDcMotorEx frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    public CachingServo frontLeftServo, backLeftServo, frontRightServo, backRightServo;

    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
        }
        return instance;
    }

    public static double frontLeftServo_init = 0.505, backLeftServo_init = 0.48, frontRightServo_init = 0.49, backRightServo_init = 0.48;
    public static double frontLeftPos = 0.505;
    public static double backLeftPos = 0.48;
    public static double frontRightPos = 0.49;
    public static double backRightPos = 0.48;

    public static double backLeft_MIN = 0.7, backLeft_MAX = 0.32;
    public static double backRight_MIN = 0.6, backRight_MAX = 0.25;
    public static double frontLeft_MIN = 0.3, frontLeft_MAX = 0.64;
    public static double frontRight_MIN = 0.36, frontRight_MAX = 0.7;


    public void initializeHardware(final HardwareMap hardwareMap){
        frontLeftMotor = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class, "frontLeftMotor"));
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backLeftMotor = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class, "backLeftMotor"));
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        frontRightMotor = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class, "frontRightMotor"));
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRightMotor = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class, "backRightMotor"));
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        frontLeftServo = new CachingServo(hardwareMap.get(Servo.class, "frontLeftServo"));
        frontLeftServo.setDirection(Servo.Direction.FORWARD);

        backLeftServo = new CachingServo(hardwareMap.get(Servo.class, "backLeftServo"));
        backLeftServo.setDirection(Servo.Direction.FORWARD);

        frontRightServo = new CachingServo(hardwareMap.get(Servo.class, "frontRightServo"));
        frontRightServo.setDirection(Servo.Direction.FORWARD);

        backRightServo = new CachingServo(hardwareMap.get(Servo.class, "backRightServo"));
        backRightServo.setDirection(Servo.Direction.FORWARD);

    }


    public void initialize(){
        frontRightServo.setPosition(frontRightServo_init);
        frontLeftServo.setPosition(frontLeftServo_init);
        backRightServo.setPosition(backRightServo_init);
        backLeftServo.setPosition(backLeftServo_init);

        frontLeftPos = 0.505;
        backLeftPos = 0.48;
        frontRightPos = 0.49;
        backRightPos = 0.48;
    }

    public void loop(double power, double direction){
        if(direction == 0){
            frontRightServo.setPosition(frontRightServo_init);
            frontLeftServo.setPosition(frontLeftServo_init);
            backRightServo.setPosition(backRightServo_init);
            backLeftServo.setPosition(backLeftServo_init);
        }
        else if(direction > 0){
            frontRightServo.setPosition(interpolateBig(direction, frontRightServo_init, frontRight_MAX));
            frontLeftServo.setPosition(interpolateBig(direction, frontLeftServo_init, frontLeft_MAX));
            backRightServo.setPosition(interpolateBig(direction, backRightServo_init, backRight_MAX));
            backLeftServo.setPosition(interpolateBig(direction, backLeftServo_init, backLeft_MAX));
        }
        else{
            frontRightServo.setPosition(interpolateSmall(direction, frontRight_MIN, frontRightServo_init));
            frontLeftServo.setPosition(interpolateSmall(direction, frontLeft_MIN, frontLeftServo_init));
            backRightServo.setPosition(interpolateSmall(direction, backRight_MIN, backRightServo_init));
            backLeftServo.setPosition(interpolateSmall(direction, backLeft_MIN, backLeftServo_init));
        }

        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);

        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
    }


    public double interpolateBig(double input, double out_min, double out_max){
        return (input - 0) * (out_max - out_min) / (1 - 0) + out_min;
    }

    public double interpolateSmall(double input, double out_min, double out_max){
        return (input - (-1)) * (out_max - out_min) / (0 - (-1)) + out_min;
    }
}
