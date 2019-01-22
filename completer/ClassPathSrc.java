package org.acm.seguin.completer;


public interface ClassPathSrc {
    public String getDesc();
    public Object getKey();
    public void reload();
    public ClassNameCache getCNC();
}

