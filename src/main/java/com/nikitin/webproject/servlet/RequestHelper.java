package com.nikitin.webproject.servlet;

import com.nikitin.webproject.command.Command;
import com.nikitin.webproject.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * This class helps to forward request to the specified command.
 */
class RequestHelper {
    private static RequestHelper instance = null;
    private HashMap<String, Command> commands = new HashMap<>();

    /**
     * Private constructor to initialize the map of available commands.
     */
    private RequestHelper(){
        commands.put("login", new LoginCommand());
        commands.put("routes", new RoutesCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("buses", new BusesCommand());
        commands.put("routeNumber", new RouteNumberCommand());
        commands.put("language", new LanguageCommand());
        commands.put("drivers", new DriversCommand());
        commands.put("appointBusToRoute", new AppointBusToRouteCommand());
        commands.put("appointDriverToBus", new AppointDriverOnBusCommand());
        commands.put("setDriverToFree", new SetDriverToFreeCommand());
        commands.put("agreeWithAppointment", new AgreeWithAppointmentCommand());
        commands.put("removeBusFromRoute", new RemoveBusFromRouteCommand());
        commands.put("addNewBus", new AddNewBusCommand());
        commands.put("addBusToDB", new AddBusToDBCommand());
    }

    /**
     * This method returns specified command from the map.
     * @param request HttpServletRequest.
     * @return command.
     */
    Command getCommand(HttpServletRequest request){

        String action = request.getParameter("command");

        Command command = commands.get(action);
        if (command == null){
            command = new LogoutCommand();
        }
        return command;
    }


    /**
     * Singleton implementation.
     * @return instance of current class.
     */
    static RequestHelper getInstance(){
        if (instance == null){
            instance = new RequestHelper();
        }
        return instance;
    }
}
