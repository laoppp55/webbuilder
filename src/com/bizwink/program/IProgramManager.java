package com.bizwink.program;

import java.util.List;

public interface IProgramManager {

    List getAllPrograms();

    List getAllBriefOfPrograms();

    int updateProgram(Program program);

    int createProgram(Program program);

    int removeProgram(int id);

    Program getAPrograms(int id);

    List getProgramByType(int ptype);

    pageOfProgram getPageOfProgram(int ptype);

    List getAllLanguageTypes();

    List getAllProgramPositions();

    List getAllProgramTypes();

    int createProgramForToolkit(programOftoolkit tprogram);

    List getAllToolkitPrograms(int siteid);

    programOftoolkit getAProgramsForToolkit(int id);

    int updateProgramForToolkit(programOftoolkit tprogram);

    int removeProgramForToolkit(int id);
}

