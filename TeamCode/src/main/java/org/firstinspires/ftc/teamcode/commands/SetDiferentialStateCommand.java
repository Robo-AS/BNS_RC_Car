package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

public class SetDiferentialStateCommand extends InstantCommand {
    public SetDiferentialStateCommand(DriveTrain.DifferentialState state){
        super(
                () -> DriveTrain.getInstance().update(state)
        );
    }
}
