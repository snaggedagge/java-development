package hottub.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {


    @Autowired
    private SettingsRepository settingsRepository;

    public void saveSettings(final SettingsDAO settings) {
        settingsRepository.save(settings);
    }

    public SettingsDAO getSettings() {
        // Same id, supposed to be only one post
        final SettingsDAO runningTimeDAO = new SettingsDAO();
        if (!settingsRepository.existsById(runningTimeDAO.getId())) {
            this.saveSettings(runningTimeDAO);
            return runningTimeDAO;
        }
        return settingsRepository.findById(runningTimeDAO.getId());
    }

}
