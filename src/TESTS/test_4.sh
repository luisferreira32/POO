: '
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE simulation SYSTEM "simulation.dtd">
<simulation finalinst="300.0" antcolsize="1000" plevel="0.5">
    <graph nbnodes="100" nestnode="1">
#'

#!/bin/bash
WEIGHT=2
for (( i = 1; i <= 100; i++ )); do
	echo "
		<node nodeidx=\"$i\">" >> test_4.xml;

	for (( j = i+1; j <= 100; j=j+2 )); do
		echo "            <weight targetnode=\"$j\">$WEIGHT</weight>" >> test_4.xml;
	done

    echo "        </node>" >> test_4.xml;
done

: '
    </graph>
    <events>
        <move alpha="1.0" beta="1.0" delta="0.2"/>
        <evaporation eta="2.0" rho="10.0"/>
    </events>
</simulation>
#'