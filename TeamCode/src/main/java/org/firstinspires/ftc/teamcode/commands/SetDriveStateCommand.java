package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

public class SetDriveStateCommand extends InstantCommand {
    public SetDriveStateCommand(DriveTrain.DriveState state){
        super(
                () -> DriveTrain.getInstance().update(state)
        );
    }
}
