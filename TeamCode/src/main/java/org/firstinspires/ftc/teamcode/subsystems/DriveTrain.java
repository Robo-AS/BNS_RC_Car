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

    public enum DirectionState{
        DUAL,
        SINGLE
    }

    public enum DriveState{
        TANK_LEFT,
        TANK_RIGHT,
        DEFAULT
    }

    public enum DifferentialState{
        IDLE,
        DISABLED
    }

    public DirectionState directionState = DirectionState.DUAL;
    public DriveState driveState = DriveState.DEFAULT;
    public DifferentialState differentialState = DifferentialState.IDLE;

    public static double frontLeftServo_init = 0.505;
    public static double backLeftServo_init = 0.48;
    public static double frontRightServo_init = 0.49;
    public static double backRightServo_init = 0.48;


    public static double frontLeftPos = 0.505;
    public static double backLeftPos = 0.48;
    public static double frontRightPos = 0.49;
    public static double backRightPos = 0.48;

    public static double backLeft_MIN = 0.7, backLeft_MAX = 0.32;
    public static double backRight_MIN = 0.6, backRight_MAX = 0.25;
    public static double frontLeft_MIN = 0.3, frontLeft_MAX = 0.64;
    public static double frontRight_MIN = 0.36, frontRight_MAX = 0.7;

    public static double TUNING_CONSTANT = 0.2;
    public static double trimCounter = 0;
    public static double TRIM_CONSTANT = 0.001;
    public static double TANK_SPEED = 1;


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

        directionState = DirectionState.DUAL;
    }

    public void initializeRaliu(){
        frontRightServo.setPosition(frontRightServo_init);
        frontLeftServo.setPosition(frontLeftServo_init);
        frontLeftPos = 0.505;
        frontRightPos = 0.49;

        directionState = DirectionState.SINGLE;
    }

    public void update(DirectionState state){
        directionState = state;
    }

    public void update(DriveState state){
        driveState = state;
    }

    public void update(DifferentialState state){
        differentialState = state;
    }

    public void loopRaliu(double power, double direction){
        power = power * 0.7;

        if(driveState == DriveState.TANK_LEFT){
            frontRightMotor.setPower(TANK_SPEED);
            frontLeftMotor.setPower(-TANK_SPEED);
            backRightMotor.setPower(TANK_SPEED);
            backLeftMotor.setPower(-TANK_SPEED);
        }
        else if(driveState == DriveState.TANK_RIGHT){
            frontRightMotor.setPower(-TANK_SPEED);
            frontLeftMotor.setPower(TANK_SPEED);
            backRightMotor.setPower(-TANK_SPEED);
            backLeftMotor.setPower(TANK_SPEED);
        }
        else{
            if(direction == 0){
                frontRightServo.setPosition(frontRightServo_init);
                frontLeftServo.setPosition(frontLeftServo_init);
            }
            else if(direction > 0){
                frontRightServo.setPosition(interpolateBig(direction, frontRightServo_init, frontRight_MAX));
                frontLeftServo.setPosition(interpolateBig(direction, frontLeftServo_init, frontLeft_MAX));
            }
            else{
                frontRightServo.setPosition(interpolateSmall(direction, frontRight_MIN, frontRightServo_init));
                frontLeftServo.setPosition(interpolateSmall(direction, frontLeft_MIN, frontLeftServo_init));
            }


            //diferential
            if(differentialState == DifferentialState.IDLE){
                if(power > 0){
                    if(direction > 0){ // right
                        frontRightMotor.setPower(power - (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                        backRightMotor.setPower(power - (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                    else{ //left
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power - (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                        backLeftMotor.setPower(power - (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                    }
                }

                else{
                    if(direction > 0){ // right
                        frontRightMotor.setPower(power + (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                        backRightMotor.setPower(power + (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                    else{ //left
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power + (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                        backLeftMotor.setPower(power + (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                    }
                }
            }
            else{//disable differential
                if(power > 0){
                    if(direction > 0){ // right
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                    else{ //left
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                }

                else{
                    if(direction > 0){ // right
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                    else{ //left
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                }
            }





        }
    }

    public void loop(double power, double direction){
        if(driveState == DriveState.TANK_LEFT){
            frontRightMotor.setPower(TANK_SPEED);
            frontLeftMotor.setPower(-TANK_SPEED);
            backRightMotor.setPower(TANK_SPEED);
            backLeftMotor.setPower(-TANK_SPEED);
        }
        else if(driveState == DriveState.TANK_RIGHT){
            frontRightMotor.setPower(-TANK_SPEED);
            frontLeftMotor.setPower(TANK_SPEED);
            backRightMotor.setPower(-TANK_SPEED);
            backLeftMotor.setPower(TANK_SPEED);
        }

        else{
            if(directionState == DirectionState.DUAL){
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
            }


            else{
                if(direction == 0){
                    frontRightServo.setPosition(frontRightServo_init);
                    frontLeftServo.setPosition(frontLeftServo_init);
                    backRightServo.setPosition(backRightServo_init);
                    backLeftServo.setPosition(backLeftServo_init);
                }
                else if(direction > 0){
                    frontRightServo.setPosition(interpolateBig(direction, frontRightServo_init, frontRight_MAX));
                    frontLeftServo.setPosition(interpolateBig(direction, frontLeftServo_init, frontLeft_MAX));
                    backRightServo.setPosition(backRightServo_init);
                    backLeftServo.setPosition(backLeftServo_init);
                }
                else{
                    frontRightServo.setPosition(interpolateSmall(direction, frontRight_MIN, frontRightServo_init));
                    frontLeftServo.setPosition(interpolateSmall(direction, frontLeft_MIN, frontLeftServo_init));
                    backRightServo.setPosition(backRightServo_init);
                    backLeftServo.setPosition(backLeftServo_init);
                }
            }


            //differential
            if(differentialState == DifferentialState.IDLE){
                if(power > 0){
                    if(direction > 0){ // right
                        frontRightMotor.setPower(power - (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                        backRightMotor.setPower(power - (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                    else{ //left
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power - (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                        backLeftMotor.setPower(power - (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                    }
                }

                else{
                    if(direction > 0){ // right
                        frontRightMotor.setPower(power + (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                        backRightMotor.setPower(power + (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                    else{ //left
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power + (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                        backLeftMotor.setPower(power + (Math.abs(direction) * TUNING_CONSTANT * Math.abs(power)));
                    }
                }
            }
            else{//disable differential
                if(power > 0){
                    if(direction > 0){ // right
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                    else{ //left
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                }

                else{
                    if(direction > 0){ // right
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                    else{ //left
                        frontRightMotor.setPower(power);
                        backRightMotor.setPower(power);

                        frontLeftMotor.setPower(power);
                        backLeftMotor.setPower(power);
                    }
                }
            }
        }


    }


    public double interpolateBig(double input, double out_min, double out_max){
        return (input - 0) * (out_max - out_min) + out_min;
    }

    public double interpolateSmall(double input, double out_min, double out_max){
        return (input - (-1)) * (out_max - out_min) + out_min;
    }



    public void increaseTrimCounter(){
        trimCounter += TRIM_CONSTANT;
        frontLeftServo_init += TRIM_CONSTANT;
        backLeftServo_init -= TRIM_CONSTANT;
        frontRightServo_init += TRIM_CONSTANT;
        backRightServo_init -= TRIM_CONSTANT;
    }

    public void decreaseTrimCounter(){
        trimCounter -= TRIM_CONSTANT;
        frontLeftServo_init -= TRIM_CONSTANT;
        backLeftServo_init += TRIM_CONSTANT;
        frontRightServo_init -= TRIM_CONSTANT;
        backRightServo_init += TRIM_CONSTANT;
    }

    public double getTrimCounter(){
        return trimCounter;
    }




    public void loopTest(){
        frontRightServo.setPosition(frontRightServo_init);
        frontLeftServo.setPosition(frontLeftServo_init);
        backRightServo.setPosition(backRightServo_init);
        backLeftServo.setPosition(backLeftServo_init);
    }
}
