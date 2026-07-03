package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

public class SetDirectionStateCommand extends InstantCommand {
    public SetDirectionStateCommand(DriveTrain.DirectionState state){
        super(
                () -> DriveTrain.getInstance().update(state)
        );
    }
}
