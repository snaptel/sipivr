package ru.sipivr.schedule.plugin;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.springframework.stereotype.Service;
import ru.sipivr.core.model.Menu;
import ru.sipivr.core.model.Module;
import ru.sipivr.core.plugin.ResultPlugin;
import ru.sipivr.core.result.AbstractResult;
import ru.sipivr.core.result.Transition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 19.01.2016.
 */
@Service
public class DateBetween extends ResultPlugin {
    private static final DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();

    @Override
    public List<AbstractResult> run(Module module, String input) {
        int nextMenu = module.getTransitionNextMenuId(0);
        if(nextMenu != 0) {
            LocalDate date1 = LocalDate.parse(module.getParameters().get(0).getValue(), parseFormat);
            LocalDate date2 = LocalDate.parse(module.getParameters().get(1).getValue(), parseFormat);

            if((LocalDate.now().isAfter(date1) || LocalDate.now().isEqual(date1)) && LocalDate.now().isBefore(date2)) {
                List<AbstractResult> res = new ArrayList<>();
                res.add(new Transition(nextMenu));
                return res;
            }
        }
        return null;
    }
}