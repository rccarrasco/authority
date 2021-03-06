package com.cervantesvirtual.MARCauthority;

/**
 * Maintain a MARC authority file
 * @author Rafael C. Carrasco
 */
public class MARCAuthorityFile { //extends Collection {
/**
    MARCAuthorityFile(String filename) {
        super(MetadataFormat.MARC, filename);
    }
*/
    /**
     * Add a new creator to the collection as a new record 
     * @param creator The new creator
     
    private void addCreator(Creator creator) {
        AuthorityRecord arecord = new AuthorityRecord(creator.getId());
        arecord.add(creator, true);
        this.add(arecord);
    }

    /**
     * Find records with names with under 1 typo per word
     * If there is an identical creator, it just returns that one.
     * @param creator A creator.
     
    public List<AuthorityRecord> findSimilarRecords(Creator creator) {
        List<AuthorityRecord> candidates = new ArrayList<AuthorityRecord>();

        for (Record record : this.getRecords()) {
            AuthorityRecord arecord = new AuthorityRecord(record);
            if (arecord.contains(creator)) {
                candidates.clear();
                candidates.add(arecord);
                return candidates;
            } else if (arecord.similar(creator)) {
                candidates.add(arecord);
            }
        }
        return candidates;
    }

    /** Read new MARC authority collection and add every record as a new record
     * or merge it with an existing one. User will be asked for input to decide 
     * if a new entry must be created, the record must be merged or the 
     * decision postponed (skip).
     * @param incollection The collection to be added
     * @return The collection of records with pending decision
     
    
    public Collection addCollection(Collection incollection) {
        Collection skipped = new Collection(MetadataFormat.MARC);
        CreatorSet increators = new CreatorSet(incollection);
        int all = 0; // store number of creator porcessed so far
        for (Creator creator : increators) {
            Integer opt = 0;
            List<AuthorityRecord> candidates = findSimilarRecords(creator);
            ++all;
            if (candidates.size() > 0) {
                if (candidates.get(0).contains(creator)) {
                    opt = null;   // do nothing
                } else {
                    System.out.println("0\t[" + creator + "] (" + all + ")");
                    for (Record record : candidates) {
                        AuthorityRecord arecord = new AuthorityRecord(record);
                        System.out.println((++opt) + "\t[" + arecord.authorized()
                                + "] " + creator.similarity(arecord.authorized()));
                    }
                    opt = Kbselector.readOption();
                }
            }
            //System.out.println("READ "+ opt); 
            if (opt == null) { // skip
                continue;
            } else if (opt == 0) {
                addCreator(creator);
            } else if (Math.abs(opt) <= candidates.size()) {
                AuthorityRecord arecord =
                        new AuthorityRecord(candidates.get(opt - 1));
                boolean typo = (opt < 0);
                arecord.add(creator, !typo);
            } else if (opt > 98) {
                System.err.println("exit");
                return skipped; // continue later
            } else { // Postpone decission 
                skipped.add(new AuthorityRecord(creator));
            }
        }
        return skipped;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: MARCAuthorityFile"
                    + "authority_file bibliographic_dir_or_files");
        } else {
            MARCAuthorityFile collection = null; //authority
            Collection skip = new Collection(MetadataFormat.MARC);
            FileOutputStream fos = null;
            for (String arg : args) {
                if (collection == null) {
                    SafeFile.backup(arg);
                    try {
                        collection = new MARCAuthorityFile(arg);
                        fos = new FileOutputStream(arg);
                    } catch (Exception e) {
                        System.err.println("Unable to open file " + arg);
                    }
                } else {
                    File dir = new File(arg);
                    if (dir.isDirectory()) {
                        for (File file : dir.listFiles()) {
                            if (file.getName().endsWith(".xml")) {
                                Collection bibliographic =
                                        new Collection(MetadataFormat.MARC,
                                        DocumentParser.parse(file));
                                Collection skipped = collection.addCollection(bibliographic);
                                skip.add(skipped);
                            }
                        }
                    } else {
                        Collection bibliographic =
                                new Collection(MetadataFormat.MARC,
                                DocumentParser.parse(dir));
                        Collection skipped = collection.addCollection(bibliographic);
                        skip.add(skipped);
                    }
                }
            }
            try {
                collection.writeXML(fos);
                fos.close();
                fos = new FileOutputStream("skip.xml");
                skip.writeXML(fos);
                fos.close();
            } catch (Exception x) {
                System.err.println("Unable to write output files");
            }
        }
    }
    * */
}
