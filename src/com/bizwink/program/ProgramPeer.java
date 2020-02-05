package com.bizwink.program;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProgramPeer implements IProgramManager {
    PoolServer cpool;

    public ProgramPeer(PoolServer cpool)
    {
        this.cpool = cpool;
    }

    public static IProgramManager getInstance()
    {
        return (IProgramManager)CmsServer.getInstance().getFactory().getProgramManager();
    }

    private static String SQL_GET_ALL_PROGRAMS = "select id,p_type,position,l_type,explain,notes,program from tbl_program_library order by p_type";

    public List getAllPrograms() {
        Connection conn = null;
        ArrayList list = new ArrayList();
        Program program;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_PROGRAMS);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                program = new Program();
                program.setId(rs.getInt("id"));
                program.setP_type(rs.getInt("p_type"));
                program.setPosition(rs.getInt("position"));
                program.setL_type(rs.getInt("l_type"));
                program.setExplain(rs.getString("explain"));
                program.setNotes(rs.getString("notes"));
                list.add(program);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String SQL_GET_BRIEF_PROGRAMS = "select id,p_type,position,l_type,explain,notes from tbl_program_library order by p_type";
    public List getAllBriefOfPrograms() {
        Connection conn = null;
        ArrayList list = new ArrayList();
        Program program;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_BRIEF_PROGRAMS);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                program = load(rs);
                list.add(program);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    private static String SQL_GET_ALL_LANGUAGETYPES = "select id,l_type from tbl_language_type order by id";

    public List getAllLanguageTypes() {
        Connection conn = null;
        ArrayList list = new ArrayList();
        Program program = null;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_LANGUAGETYPES);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                try {
                    program.setId(rs.getInt("id"));
                    program.setL_typestr(rs.getString("l_type"));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                list.add(program);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String SQL_GET_ALL_PROGRAMTYPES = "select id,position from tbl_program_type order by id";

    public List getAllProgramTypes() {
        Connection conn = null;
        ArrayList list = new ArrayList();
        Program program = null;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_PROGRAMTYPES);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                try {
                    program.setId(rs.getInt("id"));
                    program.setPositionstr(rs.getString("p_type"));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                list.add(program);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String SQL_GET_ALL_POSITIONS = "select id,position from tbl_program_position order by id";

    public List getAllProgramPositions() {
        Connection conn = null;
        ArrayList list = new ArrayList();
        Program program = null;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_POSITIONS);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                try {
                    program.setId(rs.getInt("id"));
                    program.setPositionstr(rs.getString("position"));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                list.add(program);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String SQL_GET_A_PROGRAMS = "select id,p_type,position,l_type,explain,notes,program " +
            "from tbl_program_library where id = ?";

    public Program getAPrograms(int id) {
        Connection conn = null;
        Program program = null;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_A_PROGRAMS);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                program = load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return program;
    }

    private static String SQL_GET_PROGRAMBYTYPE = "select id,p_type,position,l_type,explain,notes,program " +
            "from tbl_program_library where p_type = ?";

    public List getProgramByType(int ptype) {
        Connection conn = null;
        Program program = null;
        List plist = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_PROGRAMBYTYPE);
            pstmt.setInt(1, ptype);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                program = load(rs);
                plist.add(program);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return plist;
    }

    //private static String SQL_GET_PROGRAMBYTYPE = "select id,p_type,position,l_type,explain,notes,program " +
    //        "from tbl_program_library where p_type = ?";

    public  pageOfProgram getPageOfProgram(int ptype) {
        Connection conn = null;
        pageOfProgram pp = new pageOfProgram();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_PROGRAMBYTYPE);
            pstmt.setInt(1, ptype);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("position") ==1 &&  rs.getInt("l_type") == 1) pp.setHeader(rs.getString("program"));
                if (rs.getInt("l_type") == 2) pp.setScript(rs.getString("program"));
                if (rs.getInt("l_type") == 3) pp.setDisplay(rs.getString("program"));
                if (rs.getInt("position") ==3 &&  rs.getInt("l_type") == 1) pp.setTail(rs.getString("program"));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return pp;
    }

    private static String SQL_CREATE_PROGRAM_FOR_ORACLE = "insert into tbl_program_library(p_type,position,l_type,explain,notes,program,id)" +
            " values (?, ?, ?, ?, ?, ?, ?)";

    private static String SQL_CREATE_PROGRAM_FOR_MSSQL = "insert into tbl_program_library(p_type,position,l_type,explain,notes,program)" +
            " values (?, ?, ?, ?, ?, ?)";

    private static String SQL_CREATE_PROGRAM_FOR_MYSQL = "insert into tbl_program_library(p_type,position,l_type,explain,notes,program)" +
            " values (?, ?, ?, ?, ?, ?)";

    public int createProgram(Program program) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int errcode = 1;

        try {
            ISequenceManager sequenceMgr = SequencePeer.getInstance();
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_CREATE_PROGRAM_FOR_ORACLE);
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_CREATE_PROGRAM_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_CREATE_PROGRAM_FOR_MYSQL);
            pstmt.setInt(1, program.getP_type());
            pstmt.setInt(2, program.getPosition());
            pstmt.setInt(3, program.getL_type());
            pstmt.setString(4, program.getExplain());
            pstmt.setString(5, program.getNotes());
            DBUtil.setBigString(cpool.getType(), pstmt, 6, program.getProgram());
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt.setInt(7, sequenceMgr.getSequenceNum("ProgramLibrary"));
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            errcode = -10;
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                    errcode = -20;
                }
            }
        }

        return errcode;
    }

    private static String SQL_UPDATE_PROGRAM = "update tbl_program_library set p_type = ?,position = ?,l_type = ?,explain = ?," +
            "notes = ?, program=? where id = ?";

    public int updateProgram(Program program) {
        Connection conn = null;
        int errcode = 1;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_PROGRAM);
            pstmt.setInt(1, program.getP_type());
            pstmt.setInt(2, program.getPosition());
            pstmt.setInt(3, program.getL_type());
            pstmt.setString(4, program.getExplain());
            pstmt.setString(5, program.getNotes());
            DBUtil.setBigString(cpool.getType(), pstmt, 6, program.getProgram());
            pstmt.setInt(7, program.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            errcode = -10;
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    errcode = -20;
                    e.printStackTrace();
                }
            }
        }

        return errcode;
    }

    public int removeProgram(int id) {
        Connection conn = null;
        int errcode = 1;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from tbl_program_library where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            errcode = -10;
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    errcode = -20;
                    e.printStackTrace();
                }
            }
        }

        return errcode;
    }

    private Program load(ResultSet rs) {
        Program program = new Program();

        try {
            program.setId(rs.getInt("id"));
            program.setP_type(rs.getInt("p_type"));
            program.setPosition(rs.getInt("position"));
            program.setL_type(rs.getInt("l_type"));
            program.setExplain(rs.getString("explain"));
            program.setNotes(rs.getString("notes"));
            program.setProgram(DBUtil.getBigString(cpool.getType(), rs, "program"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return program;
    }

    //以下部分为工具箱中的程序模块管理功能
    private static String SQL_CREATE_PROGRAM_FOR_TOOLKIT = "insert into tbl_programs(siteid,programid,programname,programaddress,createdate,updatedate,id)" +
            " values (?, ?, ?, ?, ?, ?, ?)";

    public int createProgramForToolkit(programOftoolkit tprogram) {
        Connection conn = null;
        int errcode = 0;

        try {
            ISequenceManager sequenceMgr = SequencePeer.getInstance();

            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL_CREATE_PROGRAM_FOR_TOOLKIT);
            pstmt.setInt(1, tprogram.getSiteid());
            pstmt.setString(2, tprogram.getProgid());
            pstmt.setString(3, tprogram.getProgname());
            pstmt.setString(4, tprogram.getProguri());
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt.setInt(7, sequenceMgr.getSequenceNum("ProgramLibrary"));
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            errcode = -10;
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                    errcode = -20;
                }
            }
        }

        return errcode;
    }

    private static String SQL_GET_ALL_PROGRAMS_FOR_TOOLKIT = "select id,siteid,programid,programname,programaddress,createdate,updatedate from tbl_programs where siteid=?";

    public List getAllToolkitPrograms(int siteid) {
        Connection conn = null;
        ArrayList list = new ArrayList();
        programOftoolkit tprogram;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_PROGRAMS_FOR_TOOLKIT);
            pstmt.setInt(1,siteid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tprogram = loadForToolkit(rs);
                list.add(tprogram);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String SQL_GET_A_PROGRAMS_FOR_TOOLKIT = "select id,steid,programid,programname,programaddress,createdate,updatedate from tbl_programs where id = ?";

    public programOftoolkit getAProgramsForToolkit(int id) {
        Connection conn = null;
        programOftoolkit tprogram = null;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL_GET_A_PROGRAMS_FOR_TOOLKIT);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tprogram = loadForToolkit(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return tprogram;
    }

    private static String SQL_UPDATE_PROGRAM_FOR_TOOLKIT = "update tbl_programs set programid = ?,programname = ?,programaddress = ?,updatedate = ?," +
            " where id = ?";

    public int updateProgramForToolkit(programOftoolkit tprogram) {
        Connection conn = null;
        int errcode = 0;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_PROGRAM_FOR_TOOLKIT);
            pstmt.setString(1, tprogram.getProgid());
            pstmt.setString(2, tprogram.getProgname());
            pstmt.setString(3, tprogram.getProguri());
            pstmt.setTimestamp(4, tprogram.getUpdatedate());
            pstmt.setInt(5, tprogram.getId());
            pstmt.executeUpdate();

            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            errcode = -10;
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    errcode = -20;
                    e.printStackTrace();
                }
            }
        }

        return errcode;
    }

    public int removeProgramForToolkit(int id) {
        Connection conn = null;
        int errcode = 0;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from tbl_programs where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            errcode = -10;
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    errcode = -20;
                    e.printStackTrace();
                }
            }
        }

        return errcode;
    }

    private programOftoolkit loadForToolkit(ResultSet rs) {
        programOftoolkit tprogram = new programOftoolkit();

        try {
            tprogram.setId(rs.getInt("id"));
            tprogram.setSiteid(rs.getInt("siteid"));
            tprogram.setProgid(rs.getString("programid"));
            tprogram.setProgname(rs.getString("programname"));
            tprogram.setProguri(rs.getString("programaddress"));
            tprogram.setCreatedate(rs.getTimestamp("createdate"));
            tprogram.setUpdatedate(rs.getTimestamp("updatedate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tprogram;
    }
}