package io.rainrobot.wake.rest.request.preset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import io.rainrobot.wake.rest.dto.Account;
import io.rainrobot.wake.rest.dto.AlarmEvent;
import io.rainrobot.wake.rest.dto.Preset;
import io.rainrobot.wake.rest.request.account.AccountDoa;
import io.rainrobot.wake.rest.request.alarmevent.AlarmEventDoa;

@Service
public class PresetService {

    private AccountDoa accountDoa;
    private PresetDoa presetDoa;
    private AlarmEventDoa alarmEventDoa;


    public PresetService(@Autowired AccountDoa accountDoa,
                         @Autowired PresetDoa presetDoa,
                         @Autowired AlarmEventDoa alarmEventDoa) {
        this.accountDoa = accountDoa;
        this.presetDoa = presetDoa;
        this.alarmEventDoa = alarmEventDoa;

    }

    public List<Preset> findAllByUserame(String username) {
        return getAccountByUsername(username).getPresetList();
    }

    private Account getAccountByUsername(String username) {
        return accountDoa.findByUsername(username);
    }

    public void savePreset(Preset preset, String usrNm) {
        presetDoa.save(preset);
        updateLastChange(usrNm);

    }

    @Transactional
    public void deletePreset(String usrNm, int id) {
        Preset preset = presetDoa.findById(id).get();
        Account account = preset.getAccount();
        if (!usrNm.equals(account.getUsername())) {
            throw new RuntimeException("Preset with id " + id +
                    " is not found for account " +
                    usrNm);
        } else {
//                alarmEventDoa.findAllByPreset(preset).forEach(e -> {
//                    alarmEventDoa.delete(e);
//                });
            account.getPresetList().remove(preset);
            accountDoa.save(account);
            presetDoa.deleteById(id);
            updateLastChange(usrNm);
        }

    }

    private void updateLastChange(String userNn) {
        Account account = accountDoa.findByUsername(userNn);
        account.setLastChange(new Date());
        accountDoa.save(account);
    }

    @Transactional
    public Preset createPreset(String username) {
        Account account = getAccountByUsername(username);
        Preset preset = new Preset(account, "New Preset", new Date(),
                true);
        Preset saved = presetDoa.save(preset);

        account.getPresetList().add(preset);
        accountDoa.save(account);
        updateLastChange(username);

        return preset;
    }
    @Transactional
    public List<Preset> getAllPresets(String username) {
        return accountDoa.findByUsername(username).getPresetList();
    }

    public Preset getById(String username, int id) {

        Account account = accountDoa.findByUsername(username);

        return account.getPresetList().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .get();
    }

    public List<AlarmEvent> getEvents(int id) {
        Preset preset = presetDoa.findById(id).get();
        return alarmEventDoa.findAllByPreset(preset);
    }


}
