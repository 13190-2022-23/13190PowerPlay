package org.firstinspires.ftc.teamcode.teleOp.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.teleOp.subsystem.*;
import org.firstinspires.ftc.teamcode.util.Junction;

public class BaseOpMode extends CommandOpMode {

    protected MotorEx leftBack, leftFront, rightBack, rightFront, liftR, liftL;

    protected SimpleServo clawServo;

    protected ClawSubsystem claw;
    protected DriveSubsystem drive;
    protected LiftSubsystem lift;
    protected RevIMU imu;

    protected GamepadEx gamepadEx1;
    protected GamepadEx gamepadEx2;

    @Override
    public void initialize() {
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        initHardware();
        setUp();

        drive = new DriveSubsystem(leftBack, leftFront, rightBack, rightFront);
        lift = new LiftSubsystem(liftL, liftR);
        claw = new ClawSubsystem(clawServo);
        lift.setJunction(Junction.NONE);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addData("Mode", "Done initializing");
        telemetry.update();
    }


    protected void initHardware(){
        leftBack = new MotorEx(hardwareMap, "backLeft");
        leftFront = new MotorEx(hardwareMap, "frontLeft");
        rightBack = new MotorEx(hardwareMap, "backRight");
        rightFront = new MotorEx(hardwareMap, "frontRight");

        liftL= new MotorEx(hardwareMap, "slideL");
        liftR = new MotorEx(hardwareMap, "slideR");

        clawServo = new SimpleServo(hardwareMap, "claw", 0, 120);

        imu = new RevIMU(hardwareMap);
        imu.init();

    }

    protected void setUp(){
        rightFront.setInverted(true);
        leftBack.setInverted(true);
        leftFront.setInverted(true);
        liftR.setInverted(true);

        leftBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        liftL.setRunMode(Motor.RunMode.RawPower);
        liftR.setRunMode(Motor.RunMode.RawPower);
        liftL.resetEncoder();
        liftR.resetEncoder();
    }

    @Override
    public void run() {
        super.run();
        tad("leftBack Power", leftBack.motor.getPower());
        tad("leftFront Power", leftFront.motor.getPower());
        tad("rightBack Power", rightBack.motor.getPower());
        tad("rightFront Power", rightFront.motor.getPower());

        tad("liftL Power", liftL.motor.getPower());
        tad("liftR Power", liftR.motor.getPower());
        tad("liftL Position", liftL.getCurrentPosition());
        tad("liftR Position", liftR.getCurrentPosition());

        tad("Servo Position", clawServo.getPosition());

        tad("Heading", imu.getHeading());

        tad("Current Junction", lift.getCurrentJunction());
        tad("Current Cone", lift.getCurrentCone());

        tad("output", lift.getOutput());
        tad("current target", lift.getCurrentTarget());


        telemetry.update();

    }

    // gamepad button 1 = gb1
    protected GamepadButton gb1(GamepadKeys.Button button){
        return gamepadEx1.getGamepadButton(button);
    }

    // gamepad button 2 = gb2
    protected GamepadButton gb2(GamepadKeys.Button button){
        return gamepadEx2.getGamepadButton(button);
    }

    protected void tad(String tag, Object data){
        telemetry.addData(tag, data);
    }
}
