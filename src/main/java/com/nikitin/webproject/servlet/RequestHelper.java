package com.nikitin.webproject.servlet;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

class RequestHelper {
    private static RequestHelper instance = null;
    private HashMap<String, Command> commands = new HashMap<>();

    private RequestHelper(){
        commands.put("login", new LoginCommand());
        commands.put("routes", new RoutesCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("buses", new BusesCommand());
        commands.put("routeNumber", new RouteNumberCommand());
        commands.put("language", new LanguageCommand());
        commands.put("drivers", new DriversCommand());
        commands.put("appointBusToRoute", new AppointBusToRouteCommand());
        commands.put("appointDriverToBus", new AppointDriverToBusCommand());
        commands.put("setDriverToFree", new SetDriverToFreeCommand());
        commands.put("agreeWithAppointment", new AgreeWithAppointmentCommand());
        commands.put("removeBusFromRoute", new RemoveBusFromRoute());
    }

    Command getCommand(HttpServletRequest request){

        String action = request.getParameter("command");

        Command command = commands.get(action);
        if (command == null){
            command = new NoCommand();
        }
        return command;
    }

    static RequestHelper getInstance(){
        if (instance == null){
            instance = new RequestHelper();
        }
        return instance;
    }
}
