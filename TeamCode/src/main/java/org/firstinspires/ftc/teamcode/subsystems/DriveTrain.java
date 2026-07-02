package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
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

    public static double frontLeftServo_init = 0.5, backLeftServo_init = 0.5, frontRightServo_init = 0.5, backRightServo_init = 0.5;


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
    }

    public void loop(double power){
        frontRightServo.setPosition(frontRightServo_init);
        frontLeftServo.setPosition(frontLeftServo_init);
        backRightServo.setPosition(backRightServo_init);
        backLeftServo.setPosition(backLeftServo_init);

        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);

        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
    }
}
