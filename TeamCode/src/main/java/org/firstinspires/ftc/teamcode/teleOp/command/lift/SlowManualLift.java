package org.firstinspires.ftc.teamcode.teleOp.command.lift;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.teleOp.subsystem.LiftSubsystem;

import java.util.function.DoubleSupplier;

public class SlowManualLift extends CommandBase {
    private final LiftSubsystem lift;
    private final DoubleSupplier input;

    private final double slowFactor = 2; //larger = slower
    public SlowManualLift(LiftSubsystem lift, DoubleSupplier input) {
        this.lift = lift;
        this.input = input;
        addRequirements(lift);
    }

    @Override
    public void execute() {
        if(input.getAsDouble() != 0){
            lift.manualLift(input.getAsDouble()*(1/slowFactor));
            lift.stable();
        }
        else{
            lift.stable();
        }

    }

//      uncomment if needed
//    @Override
//    public boolean isFinished() {
//        return false;
//    }
}
