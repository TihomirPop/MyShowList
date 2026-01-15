package hr.tpopovic.myshowlist.adapter.out.show;

import hr.tpopovic.myshowlist.application.domain.model.ShowId;
import hr.tpopovic.myshowlist.application.port.out.DeleteShowPortResult;
import hr.tpopovic.myshowlist.application.port.out.ForDeletingShow;

public class ShowDeleter implements ForDeletingShow {

    private final ShowRepository showRepository;

    public ShowDeleter(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public DeleteShowPortResult delete(ShowId showId) {
        try {
            if (!showRepository.existsById(showId.id())) {
                return new DeleteShowPortResult.ShowNotFound();
            }

            showRepository.deleteById(showId.id());
            return new DeleteShowPortResult.Success();
        } catch (Exception e) {
            return new DeleteShowPortResult.Failure("Failed to delete show: " + e.getMessage());
        }
    }

}
