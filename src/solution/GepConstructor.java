package solution;

import logical.*;
import ga.Decoder;
import ga.GepException;
import ga.TournamentSelectionOperator;
import gep.GepInitializer;
import gep.GepMutationOperator;
import gep.GepOnePointCrossoverOperator;
import gep.bool.BDecoder;
import gep.bool.BGEP;

class GepConstructor {
    static BGEP ConstructBoolGep() throws GepException {
        BGEP gep = new BGEP();
        int itemNum = Parameter.boolIndexCount;
        gep.setFunctionSet("!&|^!&|^!&|^!&|^!&|^");

        char []varSet = new char[itemNum];
        for(int i=0; i<itemNum; i++)
        {
            varSet[i] = (char) (i+970);
        }
        gep.setVariableSet(new String(varSet));
        gep.setLinkFunction('|');
        gep.setGeneNumber(Parameter.boolGeneNumber);	//3
        gep.setGeneHead(Parameter.boolGeneHead);//8
        gep.setInitializer(new GepInitializer(gep, Parameter.boolGepInitializer));//种群大小为500
        gep.setSelectionOperator(new TournamentSelectionOperator(gep, 4));
        gep.addMutationOperator(new GepMutationOperator(gep, 0.04));
        gep.addCrossoverOperator(new GepOnePointCrossoverOperator(gep, 0.4));

        Decoder decoder = new BDecoder(gep);
        gep.setDecoder(decoder);

        return gep;
    }

    static LogicalGep ConstructLogicalGep() throws GepException {
        int itemNum = Parameter.logicalIndexCount;
        LogicalGep gep = new LogicalGep();
        gep.setFunctionSet("+-*/+-*/+-*/+-*/+-*/+-*/+-*/+-*/+-*/+-*/+-*/+-*/+-*/");

        char []varSet = new char[itemNum];
        for (int i = 0; i < itemNum; i++) {
            varSet[i] = (char) (i + 970);
        }
        gep.setVariableSet(new String(varSet));

        gep.setConstantSet("");

        gep.setLinkFunction('+');

        gep.setRelationSet("<>{}");

        gep.setGeneNumber(Parameter.logicalGeneNumber);
        gep.setGeneHead(Parameter.logicalGeneHead);

        gep.setInitializer(new LogicalInitializer(gep, Parameter.logicalGepInitializer));
        gep.setSelectionOperator(new TournamentSelectionOperator(gep, 4));

        gep.addMutationOperator(new LogicaMutationOperator(gep, 0.04));
        gep.addMutationOperator(new LogicalRelationMutationOperator(gep, 0.04));

        gep.addCrossoverOperator(new LogicalOnePointCrossoverOperator(gep, 0.4));

        Decoder decoder = new LogicalDecoder(gep);
        gep.setDecoder(decoder);

        return gep;
    }
}
