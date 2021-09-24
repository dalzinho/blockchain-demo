package uk.co.mrdaly.blockchaindemo.validator;

import org.springframework.stereotype.Component;
import uk.co.mrdaly.blockchaindemo.model.Block;

import java.util.List;
import java.util.Optional;

@Component
public class Validator {

    public Optional<Block> getFirstInvalidBlock(List<Block> blockList) {
        boolean isValid;
        Block offendingBlock = null;
        for (int i = 0; i < blockList.size(); i++) {
            Block bloc = blockList.get(i);
            if (i == 0) {
                isValid = bloc.getPreviousHash().equals("");
            } else {
                isValid = bloc.getPreviousHash().equals(blockList.get(i - 1).getHash());
            }

            if (!isValid) {
                offendingBlock = bloc;
                break;
            }
        }
        return Optional.ofNullable(offendingBlock);
    }

}
