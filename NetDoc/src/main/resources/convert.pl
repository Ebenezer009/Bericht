#!/usr/bin/perl

# Recursively converts outdated NetDoc doctex files to the current version.
# Takes a root directory path as a paramter.


my $CURRENT_VERSION = '1.0';


if (($#ARGV < 0) || ($ARGV[0] =~ /^-{0,1}(h(elp){0,1}|\?)$/)) {
	printUsage();
}
else {
    my $rootPath = $ARGV[0];
    if (-d $rootPath) {
        convertDir($rootPath);
    }
    else {
        print "Error: directory '$rootPath' does not exist\n";
    }
}


exit;


sub printUsage {
    print <<"END";

NetDoc doctex conversion tool
Converts outdated NetDoc doctex files to the current version ($CURRENT_VERSION).

Usage: convert <RootPath>
       convert [-?|-h|-help]

<RootPath> specifies the root directory containing the documentation files.
Recursively searches in that directory for old files and replaces them.

END
}


sub convertDir {
    my $basePath = shift;

    if ($basePath =~ /^.*[^\/]$/) {
        $basePath .= '/';
    }

    my @files = glob($basePath.'*');
    
    foreach my $file (@files) {
        if (-d $file) {
            convertDir($file);
        }
        elsif ($file =~ /^.*\.doctex$/) {
            convert($file);
        }
    }
}


sub convert {
    my $file = shift;

    if (! open(FILE, "<$file")) {
        print "Error: Unable to open file '$file' for reading\n";
        return;
    }
    my @lines = <FILE>;
    close (FILE);

    foreach my $line (@lines) {
        chomp($line);
    }

    my $version = '';
    if (@lines != 0) {
        if ($lines[0] eq '% NetDoc IFA drawing documentation file v1.0') {
            $version = "0.8";
        }
        elsif ($lines[0] eq '% NetDoc documentation unit v1.0') {
            $version = "0.9";
        }
        elsif ($lines[0] =~ /^% NetDoc TeX Documentation Unit v(\d\.\d)$/) {
            $version = $1;
        }
    }
    if (($version eq '') || ($version ge $CURRENT_VERSION)) {
        print "Info: File '$file' is not an outdated NetDoc file\n";
        return;
    }
    
    if ($version eq '0.8') {
        my @newLines = ("% NetDoc documentation unit v1.0");
        for (my $index = 1; $index < @lines; $index++) {
            my $line = $lines[$index];
            if ($line eq "\\netdoc-begin{netdoc-document}") {
                push (@newLines, "\\begin{netdocDocument}");
                push (@newLines, "\\begin{netdocMain}");
            }
            elsif ($line eq "\\netdoc-end{netdoc-document}") {
                push (@newLines, "\\end{netdocMain}");
                push (@newLines, "\\end{netdocDocument}");
            }
            elsif ($line =~ /^\\netdoc-date-c(.*)$/) {
                push (@newLines, "\\netdocDateC$1");
            }
            elsif ($line =~ /^\\netdoc-date-last-m(.*)$/) {
                push (@newLines, "\\netdocDateLastM$1");
            }
            elsif ($line =~ /^\\netdoc-drawing-n(.*)$/) {
                push (@newLines, "\\netdocDrawingN$1");
            }
            elsif ($line =~ /^\\netdoc-drawing-p(.*)$/) {
                push (@newLines, "\\netdocDrawingP$1");
            }
            elsif ($line =~ /^\\netdoc-author(.*)$/) {
                push (@newLines, "\\netdocAuthor$1");
            }
            elsif ($line =~ /^\\netdoc-title(.*)$/) {
                push (@newLines, "\\netdocTitle$1");
            }
            elsif ($line =~ /^\\netdoc-(begin|end)(\{netdoc)-d(.*)$/) {
                push (@newLines, "\\$1$2D$3");
            }
            else {
                push (@newLines, "$line");
            }
        }
        @lines = @newLines;
        $version = '0.9';
    }
    
    if ($version eq '0.9') {
        $lines[0] = "% NetDoc TeX Documentation Unit v1.0";
        $version = '1.0';
    }
    
    if (! open(FILE, ">$file")) {
        print "Error: Could not open file '$file' for writing\n";
        return;
    }
    
    for (my $index = 0; $index < @lines; $index++) {
        print FILE "$lines[$index]\n";
    }

    close (FILE);
    
    print "Info: File '$file' successfully converted\n";
}
