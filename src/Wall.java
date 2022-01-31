import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wall implements Structure {
    private final List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }


    /**
     * Method to flatten all blocks containing another children (CompositeBlock)
     *
     * @param blocks List of blocks which should be flattened
     * @return Stream of block obtained by flattening (summing up) all children
     */
    private Stream<Block> getStream(List<Block> blocks) {
        return blocks.stream().flatMap(block -> {
            if (block instanceof CompositeBlock) {
                return Stream.concat(
                        getStream(((CompositeBlock) block).getBlocks()),
                        Stream.of(block)
                );
            } else {
                return Stream.of(block);
            }
        });
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return getStream(blocks)
                .filter(block -> block.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return getStream(blocks)
                .filter(block -> block.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) getStream(blocks).count();
    }
}

