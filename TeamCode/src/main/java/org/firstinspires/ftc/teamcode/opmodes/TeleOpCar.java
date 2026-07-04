package org.firstinspires.ftc.teamcode.opmodes;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DecreaseTrimCommand;
import org.firstinspires.ftc.teamcode.commands.EngageArm;
import org.firstinspires.ftc.teamcode.commands.IncreaseTrimCommand;
import org.firstinspires.ftc.teamcode.commands.SetDiferentialStateCommand;
import org.firstinspires.ftc.teamcode.commands.SetDirectionStateCommand;
import org.firstinspires.ftc.teamcode.commands.SetDriveStateCommand;
import org.firstinspires.ftc.teamcode.hardware.Car;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOpCar🚗", group = "OpModes")
public class TeleOpCar extends CommandOpMode {
    private final Car car = Car.getInstance();
    public GamepadEx gamepadEx;
    private double loopTime = 0;


    @Override
    public void initialize() {
        gamepad1.setLedColor(128, 0, 128, Gamepad.LED_DURATION_CONTINUOUS);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        CommandScheduler.getInstance().reset();
        gamepadEx = new GamepadEx(gamepad1);
        car.initializeHardware(hardwareMap);
        car.initialize();

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new DecreaseTrimCommand());
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new IncreaseTrimCommand());

        gamepadEx.getGamepadButton(GamepadKeys.Button.A)//cross
                .whenPressed(new EngageArm());
        gamepadEx.getGamepadButton(GamepadKeys.Button.Y)//triangle
                .whenPressed(
                        () -> CommandScheduler.getInstance().schedule(
                                new ConditionalCommand(
                                        new SetDirectionStateCommand(DriveTrain.DirectionState.SINGLE),
                                        new SetDirectionStateCommand(DriveTrain.DirectionState.DUAL),
                                        () -> car.driveTrain.directionState == DriveTrain.DirectionState.DUAL
                                )
                        )
                );

        gamepadEx.getGamepadButton(GamepadKeys.Button.B)//sqaure
                .whenPressed(new SetDriveStateCommand(DriveTrain.DriveState.TANK_LEFT));
        gamepadEx.getGamepadButton(GamepadKeys.Button.B)//sqaure
                .whenReleased(new SetDriveStateCommand(DriveTrain.DriveState.DEFAULT));


        gamepadEx.getGamepadButton(GamepadKeys.Button.X)//circle
                .whenPressed(new SetDriveStateCommand(DriveTrain.DriveState.TANK_RIGHT));
        gamepadEx.getGamepadButton(GamepadKeys.Button.X)//circle
                .whenReleased(new SetDriveStateCommand(DriveTrain.DriveState.DEFAULT));



        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
                .whenPressed(
                        () -> CommandScheduler.getInstance().schedule(
                                new ConditionalCommand(
                                        new SetDiferentialStateCommand(DriveTrain.DifferentialState.DISABLED),
                                        new SetDiferentialStateCommand(DriveTrain.DifferentialState.IDLE),
                                        () -> car.driveTrain.differentialState == DriveTrain.DifferentialState.IDLE
                                )
                        )
                );






    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        car.bulkRead();
        car.loop(gamepad1.left_stick_y, gamepad1.right_stick_x);


//        if(gamepad1.triangle){
//            if(car.driveTrain.directionState == DriveTrain.DirectionState.DUAL){
//                car.driveTrain.directionState = DriveTrain.DirectionState.SINGLE;
//            }
//            else{
//                car.driveTrain.directionState = DriveTrain.DirectionState.DUAL;
//            }
//        }




        telemetry.addData("TRIM:", car.driveTrain.getTrimCounter());
        telemetry.addData("Directie:", car.driveTrain.directionState);
        telemetry.addData("Differential:", car.driveTrain.differentialState);

        double loop = System.nanoTime();
        telemetry.addData("Hz", 1000000000 / (loop - loopTime));
        loopTime = loop;
        telemetry.update();

    }
}
