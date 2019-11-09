import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util {
	
	public static final int A4_WIDTH = 210;
	public static final int A4_HEIGHT = 297;
	
	public static final float A4_WEIGHT = 2.74f;
	
	public static void createLayoutFile() {
		File layoutDirectory = new File("./layouts");
		if(!layoutDirectory.exists())
			layoutDirectory.mkdir();
		
		String[] defcontents = {
				"outsideDotBorder;41;68;493;676;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"titleL;137;80;300;100;결       석       계;함초롬돋움, 굵게, 30;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"numberL;320;165;100;20;학  번  :  $number;함초롬돋움, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"nameL;320;188;100;20;이  름  :  $name;함초롬돋움, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content1L;75;225;400;20;위  학생은  다음과  같은  사유로  인하여  결석계를  제출합니다.;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content2L;85;253;380;20;1.  결석  사유  :    현장체험학습으로  인한  인정결;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content3L;85;278;420;20;2.  결석  기간  :    $asYear년   $asMonth월   $asDay일  ~   $adMonth월   $adDay일 (총  $aDays 일간);함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content4L;187;360;200;20;$year년   $month월     $day일;함초롬바탕, 보통, 12;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content5L;213;400;300;20;보호자(학생과  관계:  모  )            $parentName      (인);함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"insideDotBorder;77;445;421;228;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"content6L;97;475;250;20;※  담임교사의  의견서;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content7L;105;508;250;20;1. 결석 유형  :    인정결;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content8L_1;105;528;250;20;2. 내;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content8L_2;163;528;250;20;용  :    현장체험학습;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content9L;105;563;250;20;3. 확인 방법  :    내부결재;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content10L;237;600;250;20;담임교사              $teacher         (인);함초롬바탕, 보통, 13;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content11L;237;620;250;20;출결담당교사                                 (인);함초롬바탕, 보통, 13;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"footer;55;685;250;30;화명고등학교장  귀하;함초롬바탕, 굵게, 22;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;",
				
				"outsideDotBorder;41;68;493;676;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"titleL;137;78;300;35;현장 체험 학습 계획서;함초롬돋움, 굵게, 30;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;true;true;\n" + 
				"outsideTableBorder;64;132;446;444;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableNormalBorder1;64;132;64;444;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableNormalBorder2;64;132;446;106;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableNormalBorder3;64;237;446;78;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableDotBorder1;64;132;446;29;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableDotBorder2;64;160;446;46;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableDotBorder3;127;237;383;48;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableNormalBorder4;203;237;76;78;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableNormalBorder5;356;237;76;78;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableDotBorder4;64;314;446;49;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"content1L;65;133;62;28;학  생;함초롬바탕, 굵게, 11;R:51, G:51, B:51;가운데;;0;;R:229, G:229, B:229;true;true;\n" + 
				"content2L;65;161;62;45;일  시;함초롬바탕, 굵게, 11;R:51, G:51, B:51;가운데;;0;;R:229, G:229, B:229;true;true;\n" + 
				"content3L;65;206;62;31;장  소;함초롬바탕, 굵게, 11;R:51, G:51, B:51;가운데;;0;;R:229, G:229, B:229;true;true;\n" + 
				"content4L_2;64;256;64;20;학습방법;함초롬바탕, 굵게, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content4L_3;64;274;64;20;(신청유형);함초롬바탕, 굵게, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content4L_1;64;237;64;78;;;;;;0;;R:229, G:229, B:229;true;true;\n" + 
				"content5L;64;314;64;49;학습주제;함초롬바탕, 굵게, 11;R:51, G:51, B:51;가운데;;0;;R:229, G:229, B:229;true;true;\n" + 
				"content6L;65;445;62;20;학 습 할;함초롬바탕, 굵게, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content6L_1;65;462;62;20;내  용;함초롬바탕, 굵게, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"contenet6L_2;64;362;64;214;;;;;;0;;R:229, G:229, B:229;true;true;\n" + 
				"content7L_1;128;242;75;20;친.인척방문;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content7L_2;128;259;75;20;체험학습;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content8L_1;204;238;74;17;고적답사.;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content8L_2;204;250;74;20;향토행사참석;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content8L_3;204;267;74;16;체험학습;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content9L_1;279;242;77;20;가족동반여행;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content9L_2;279;259;77;20;체험학습;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content10L_1;357;242;74;20;국외;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content10L_2;357;259;74;20;체험학습;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content11L;432;238;77;47;기타;함초롬바탕, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content12L;145;133;364;27;$sGrade  학년          $sClass  반       $sNumber  번      이름  :  $name;함초롬바탕, 보통, 11;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content13L;145;161;364;44;$asYear년   $asMonth 월   $asDay 일  ~   $adMonth 월   $adDay 일 (총  $aDays 일간);함초롬바탕, 보통, 11;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content14L;209;593;160;20;$year년    $month월    $day일;함초롬바탕, 보통, 14;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content15L;282;625;75;20;신청인    학;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content16L;363;625;30;20;생 :;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content17L;396;625;102;20;$name        인;함초롬바탕, 보통, 13;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content18L;337;650;52;20;학부모 :;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content19L;396;650;102;20;$parentName        인;함초롬바탕, 보통, 13;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content20L;55;687;250;30;화 명 고 등 학 교 장  귀 하;함초롬돋움, 굵게, 21;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;",
				
				"outsideDotBorder;41;68;493;676;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"content1L;187;120;200;40;학부모  동의서;함초롬돋움, 보통, 31;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content2L;323;197;20;20;학;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content3L;359;197;170;20;번 :  $sGrade 학년   $sClass 반   $sNumber 번;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content4L;323;222;20;20;성;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content5L;359;222;170;20;명 :  $name;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content6L;80;295;450;20;위  학생은  $asYear년   $asMonth월    $asDay일부터    $adMonth월    $adDay일까지  체류하게;함초롬바탕, 보통, 14;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content7L;65;327;400;20;되었습니다. (총  $aDays 일간);함초롬바탕, 보통, 14;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content8L;80;359;450;20;이 기간 동안 학생의 안전과 지도는 전적으로 보호자의 자율적 판단에;함초롬바탕, 보통, 14;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content9L;65;391;450;20;따라 행해지며, 학교 측에 일체의 책임을 묻지 않음에 동의합니다.;함초롬바탕, 보통, 14;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content10L;187;500;200;20;$year년     $month월      $day일;함초롬바탕, 보통, 15;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content11L;300;550;220;20;보호자         $parentName        (인);함초롬바탕, 보통, 15;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content12L;60;610;300;30;화 명 고 등 학 교 장  귀 하;함초롬돋움, 굵게, 24;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;",
				
				"outsideDotBorder;41;68;493;676;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"outsideTableBorder;65;198;445;368;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"content1L;87;100;400;40;현장 체험 학습 결과 보고서;함초롬돋움, 굵게, 30;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content2L;291;170;200;20;$sGrade 학년   $sClass 반   $sNumber 번   이름 : $name;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content3L;65;198;85;33;학습  기간;함초롬바탕, 굵게, 12;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content4L;65;232;85;33;장    소;함초롬바탕, 굵게, 12;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content5L_1;65;368;85;20;학습  내용;함초롬바탕, 굵게, 12;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content5L_2;65;392;85;20;및;함초롬바탕, 굵게, 12;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"Object2599;65;415;85;20;결        과;함초롬바탕, 굵게, 12;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"insideTableDotBorder1;65;232;445;34;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"insideTableNormalBorder1;65;198;85;368;;;;;실선;1;R:0, G:0, B:0;R:229, G:229, B:229;true;true;\n" + 
				"content6L;165;199;344;33;$asYear년    $asMonth월    $asDay일  ~    $adMonth월    $adDay일 (총  $aDays 일간);함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content7L;65;570;300;20;위와 같이 현장 체험 학습 결과 보고서를 제출합니다.;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content8L;187;603;200;20;$year년     $month월      $day일;함초롬바탕, 보통, 14;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content9L;263;635;119;20;보호자(관계 :  모  );함초롬바탕, 보통, 14;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content10L;355;635;161;20;$parentName         (인);함초롬바탕, 보통, 14;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content11L;263;657;103;20;확인 : 담임교사;함초롬바탕, 보통, 14;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content12L;298;657;220;20;$teacher         (인);함초롬바탕, 보통, 14;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content13L;60;691;300;30;화 명 고 등 학 교 장  귀 하;함초롬돋움, 굵게, 24;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;",
				
				"outsideDoubleBorder;41;68;493;676;;;;;이중 실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"titleL;137;109;300;40;결       석       계;함초롬돋움, 굵게, 30;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"numberL;320;160;150;20;학  번  :  $number;함초롬돋움, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"nameL;320;183;150;20;이  름  :  $name;함초롬돋움, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content1L;75;220;400;20;위  학생은  다음과  같은  사유로  인하여  결석계를  제출합니다.;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content2L;85;245;380;20;1.  결석  사유  :    $reason;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content3L;85;269;430;20;2.  결석  기간  :    $asYear년    $asMonth월    $asDay일  ~    $adMonth월    $adDay일 (총  $aDays 일간);함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content4L;85;293;380;20;3.  서류  첨부  :    유 (    ○    ),  무 (      );함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content5L;187;345;200;20;$year년     $month월        $day일;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content6L;213;382;310;20;보호자(학생과  관계:  모  )             $parentName          (인);함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"insideDotBorder;78;425;421;241;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"content7L;97;457;150;20;※  담임교사의  의견서;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content8L;105;488;250;20;1. 결석 유형  :    $aType;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content9L_1;105;508;34;20;2. 내;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content9L_2;163;508;250;20;용  :    $reason;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content10L;105;548;250;20;3. 확인 방법  :    $aMethod;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content11L;247;590;100;20;담임교사;함초롬바탕, 보통, 13;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content12L;278;590;210;20;$teacher      (인);함초롬바탕, 보통, 13;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content13L;197;610;150;20;출결담당교사;함초롬바탕, 보통, 13;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content14L;458;610;30;20;(인);함초롬바탕, 보통, 13;R:51, G:51, B:51;오른쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"footer;63;687;250;30;화명고등학교장  귀하;함초롬바탕, 굵게, 22;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;",
				
				"outsideDoubleBorder;41;68;493;676;;;;;이중 실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"titleL;137;98;300;50;출결  인정  확인서;함초롬돋움, 굵게, 30;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"numberL;329;170;150;20;학  번  :  $number;함초롬돋움, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"nameL;329;193;150;20;이  름  :  $name;함초롬돋움, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content1L;75;230;400;20;위  학생은  다음과  같은  사유로  인하여  확인서를  제출합니다.;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content2L;85;257;380;20;1.  출결  인정  내용  :    $reason;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content3L;85;283;380;20;2.  출결  인정  기간  :    $aYear년    $aMonth월    $aDay일    $aPeriod;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content4L;85;309;380;20;※ 지각, 조퇴, 결과와 관련된 서류를 첨부해주세요.;함초롬바탕, 보통, 12;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content5L;187;348;200;20;$year년      $month월      $day일;함초롬바탕, 보통, 12;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;true;true;\n" + 
				"insideDotBorder;77;395;422;121;;;;;점선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"content6L;97;397;250;20;※  담임교사의  의견서;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content7L;105;417;250;20;1.  확인  내용  :  $reason;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;false;true;\n" + 
				"content8L;105;455;300;20;2.  확인  방법  :  $aMethod;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"content9L;332;482;165;20;담임교사      $teacher    인;함초롬바탕, 보통, 13;R:51, G:51, B:51;왼쪽;;0;;R:255, G:255, B:255;true;true;\n" + 
				"footer;162;540;250;30;화명고등학교장 귀하;함초롬바탕, 굵게, 22;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"footerBorder1;264;596;241;82;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"footerBorder2;264;613;241;3;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"footerBorder3;264;596;60;82;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"footerBorder4;388;596;60;82;;;;;실선;1;R:0, G:0, B:0;R:255, G:255, B:255;false;true;\n" + 
				"footerLabel1;264;596;60;17;계;함초롬돋움, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"footerLabel2;323;596;66;17;교무부장;함초롬돋움, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"footerLabel3;388;596;60;17;교감;함초롬돋움, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"footerLabel4;447;596;58;17;교장;함초롬돋움, 보통, 11;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;false;true;\n" + 
				"footerLabel5;388;615;60;63;전결;바탕, 보통, 15;R:51, G:51, B:51;가운데;;0;;R:255, G:255, B:255;true;true;"
		};
		int[] vals = {11, 12, 13, 14, 21, 31};
		
		for(int i=0; i<vals.length; i++) {
			File f = new File("./layouts/layout" + vals[i] + ".hclay");
			
			String tmpPath = Util.getConfig("layout" + vals[i] + "Path");
			if(tmpPath != null)
				if(new File(tmpPath).exists())
					continue;
			
			if(!f.exists()) {
				BufferedWriter bw = null;
				
				try {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF8"));
					
					String[] splits = defcontents[i].split("\n");
					for(int j=0; j<splits.length; j++) {
						bw.write(splits[j]);
						if(j != splits.length - 1)
							bw.newLine();
					}
					
					Util.changeConfig("layout" + vals[i] + "Path", f.getAbsolutePath().replace(".\\", "").replace("./", ""));
				} catch(IOException e) {
					e.printStackTrace(System.err);
				} finally {
					if(bw != null)
						try { bw.close(); } catch(IOException e) { e.printStackTrace(System.err); }
				}
			}
		}
	}
	
	public static void createDataFile() {
		File dataFile = new File("./data.hc");
		
		if(!dataFile.exists()) {
			FileOutputStream fos = null;
			OutputStreamWriter osw = null;
			BufferedWriter bw = null;
			
			try {
				fos = new FileOutputStream("./data.hc");
				osw = new OutputStreamWriter(fos, "UTF8");
				bw = new BufferedWriter(osw);
				
				bw.write("# 맨 앞에 '#' 을 입력하면, 주석처리가 됩니다.");
				bw.newLine();
				bw.newLine();
				bw.write("# < 작성법 >");
				bw.newLine();
				bw.write("# 학번:이름:학부모명(모)");
				bw.newLine();
				bw.write("# 예) 10101:OOO:AAA");
				bw.newLine();
			} catch(IOException e) {
				e.printStackTrace(System.err);
			} finally {
				if(bw != null)
					try { bw.close(); } catch(IOException e) { e.printStackTrace(System.err); }
				if(osw != null)
					try { osw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
				if(fos != null)
					try { fos.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	public static ArrayList<E_Student> readData() {
		ArrayList<E_Student> result = new ArrayList<E_Student>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		Util.createDataFile();
		
		try {
			fis = new FileInputStream("./data.hc");
			isr = new InputStreamReader(fis, "UTF8");
			br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.length() == 0)
					continue;
				if(line.charAt(0) == 0xFEFF)
					line = line.substring(1);
				if(line.charAt(0) == '#')
					continue;
				
				String[] read = line.split(":");
				if(read.length != 3) continue;
				
				try {
					int number = Integer.parseInt(read[0]);
					String name = read[1];
					String parentName = read[2];
					
					result.add(new E_Student(number, name, parentName)); 
				} catch (NumberFormatException ex) { continue; }
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(isr != null)
				try { isr.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(fis != null)
				try { fis.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public static void changeConfig(String key, String value) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			String json = "";
			File f = new File("config.hc");
			if(!f.exists()) {
				f.createNewFile();
				json = "{}";
			} else {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("config.hc"), "UTF8"));
				String line;
				while((line = br.readLine()) != null) {
					if(line.charAt(0) == 65279)
						line = line.substring(1);
					json += line;
				}
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			}
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject)parser.parse(json);
			object.put(key, value);
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("config.hc"), "UTF8"));
			String s = object.toJSONString();
			bw.write(s);
			
			try { bw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			
			
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace(System.err);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(bw != null)
				try { bw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
	}
	
	public static String getConfig(String key) {
		BufferedReader br = null;
		
		try {
			String json = "";
			File f = new File("config.hc");
			if(!f.exists()) {
				return null;
			} else {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("config.hc"), "UTF8"));
				String line;
				while((line = br.readLine()) != null) {
					if(line.charAt(0) == 65279)
						line = line.substring(1);
					json += line;
				}
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			}
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject)parser.parse(json);
			return (String)object.get(key);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace(System.err);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		return null;
	}
	
	/**JOptionPane 의 showMessageDialog 를 통해 간단히 메세지를 보여준다.
	 * @param message : 보여줄 메세지
	 * @param type : 메세지의 타입
	 * @see javax.swing.JOptionPane
	 */
	public static void showMessage(String message, int type) {
		JFrame tmp = new JFrame();
		JOptionPane.showMessageDialog(tmp, message, "알림", type);
		tmp.dispose();
	}
	
	/**FileDialog 를 통해 선택한 파일의 절대경로를 리턴한다.
	 * @return 선택한 파일의 절대경로
	 * @see java.awt.FileDialog
	 */
	public static String getPathByFileDialog() {
		JFrame f = new JFrame();
		FileDialog fd = new FileDialog(f, "파일 선택");
		fd.setVisible(true);
		f.dispose();
		
		if(fd.getDirectory() == null || fd.getFile() == null)
			return null;
		
		return fd.getDirectory() + fd.getFile(); 
	}
	
	/**Windows 의 cmd 명령어를 실행시킨다.
	 * @param cmd : 실행시킬 명령어
	 * @return 명령어 실행 결과
	 */
	public static String execCommand(String cmd) {
		Process process = null;
		BufferedReader br = null;
		StringBuffer readBuffer = null;
		
		try {
			cmd = "cmd.exe /c " + cmd;
			
			process = Runtime.getRuntime().exec(cmd);
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			readBuffer = new StringBuffer();
			
			while((line = br.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
			}
			
			return readBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		return null;
	}
	
	/**매개변수로 넣은 버튼에 여러가지 서식을 입힙니다. JButton, JMenuItem, JToggleButton 은 다른 메서드를 사용해야 합니다.
	 * @param button : 서식을 적용할 컴포넌트
	 * @param c : 컴포넌트에 입힐 색깔
	 * @return 서식을 모두 적용한 컴포넌트를 리턴한다.
	 * @see javax.swing.JComponent
	 * @see #getDefaultComponent(JComponent, Color, boolean)
	 */
	public static JComponent getDefaultComponent(JComponent component, Color c) {
		component.setOpaque(true);
		component.setBackground(c);
		return component;
	}
	
	/**매개변수로 넣은 컴포넌트에 여러가지 서식을 입힙니다. JButton, JMenuItem, JToggleButton 만 사용 가능합니다.
	 * @param component : 서식을 적용할 컴포넌트
	 * @param c : 컴포넌트에 입힐 색깔
	 * @param isBorderPainted : 컴포넌트의 외곽선 여부
	 * @return 서식을 모두 적용한 컴포넌트를 리턴한다. 맞지 않는 컴포넌트인 경우 null 을 반환한다.
	 * @see javax.swing.AbstractButton
	 * @see javax.swing.JButton
	 * @see javax.swing.JMenuItem
	 * @see javax.swing.JToggleButton
	 */
	public static JComponent getDefaultComponent(JComponent component, Color c, boolean isBorderPainted) {
//		component.setFont(new Font("돋움", Font.PLAIN, 13));
		if(component instanceof JButton) {
			JButton tmpButton = (JButton) component;
			tmpButton.setBorderPainted(isBorderPainted);
			tmpButton.setFocusPainted(false);
			tmpButton.setOpaque(true);
			tmpButton.setBackground(c);
			return tmpButton;
		} else if(component instanceof JMenuItem) {
			JMenuItem tmpMenuItem = (JMenuItem) component;
			tmpMenuItem.setBorderPainted(isBorderPainted);
			tmpMenuItem.setFocusPainted(false);
			tmpMenuItem.setOpaque(true);
			tmpMenuItem.setBackground(c);
			return tmpMenuItem;
		} else if(component instanceof JToggleButton) {
			JToggleButton tmpToggle = (JToggleButton) component;
			tmpToggle.setBorderPainted(isBorderPainted);
			tmpToggle.setFocusPainted(false);
			tmpToggle.setOpaque(true);
			tmpToggle.setBackground(c);
			return tmpToggle;
		}
		return null;
	}
	
	/**선생님의 성함을 입력 받고, 저장하는 Dialog 를 띄웁니다.
	 * @see javax.swing.JOptionPane
	 */
	public static void teacherNameDialog() {
		String teacherName = JOptionPane.showInputDialog("프로그램을 사용하시는 사용자의 이름을 입력하세요.");
		if(teacherName == null)
			System.exit(0);
		if(teacherName.equals(""))
			System.exit(0);
		Util.changeConfig("teacher", teacherName);
	}
	
	/**색깔을 받아 문자열로 반환한다.
	 * @see java.awt.Color
	 * @param c 변환할 색깔
	 * @return 색깔을 문자열로 변환한 결과
	 */
	public static String colorToString(Color c) {
		if(c == null)
			return "R:0, G:0, B:0";
		return String.format("R:%d, G:%d, B:%d", c.getRed(), c.getGreen(), c.getBlue());
	}
	
	/**문자열을 받아 색깔로 반환한다.<br>
	 * 문자열의 형식은 "R:r, G:g, B:b" (여기서 r, g, b 는 각각의 RGB 값)
	 * @see java.awt.Color
	 * @param s 변환할 문자열
	 * @return 문자열을 색깔로 변환한 결과
	 */
	public static Color stringToColor(String s) throws NumberFormatException, ArrayIndexOutOfBoundsException {
		String[] strs = s.split(", ");
		
		int r = Integer.parseInt(strs[0].split(":")[1]);
		int g = Integer.parseInt(strs[1].split(":")[1]);
		int b = Integer.parseInt(strs[2].split(":")[1]);
		
		return new Color(r, g, b);
	}
	
	/**글꼴을 받아 문자열로 반환한다.
	 * @see java.awt.Font
	 * @param f 변환할 글꼴
	 * @return 글꼴을 문자열로 변환한 결과
	 */
	public static String fontToString(Font f) {
		String fontName = f.getFontName();
		
		String fontString = fontName + ", ";
		if(fontName.contains("Bold"))
			fontString = fontName.substring(0, fontName.length() - 5) + ", ";
		if(f.getStyle() == Font.PLAIN)
			fontString += "보통";
		else if(f.getStyle() == Font.BOLD)
			fontString += "굵게";
		else if(f.getStyle() == Font.ITALIC)
			fontString += "기울임꼴";
		else if(f.getStyle() == (Font.BOLD | Font.ITALIC))
			fontString += "굵은 기울임꼴";
		fontString += ", " + f.getSize();
		return fontString;
	}
	
	/**문자열을 받아 색깔로 반환한다.<br>
	 * 문자열의 형식은 "<글꼴이름>, <글꼴 스타일>, <글꼴 크기>"
	 * @see java.awt.Font
	 * @param s 변환할 문자열
	 * @return 문자열을 색깔로 변환한 결과
	 */
	public static Font stringToFont(String s) throws NumberFormatException, ArrayIndexOutOfBoundsException {
		String[] strs = s.split(", ");
		int fontSize = Integer.parseInt(strs[2]);
		
		if(strs[1].equals("보통"))
			return new Font(strs[0], Font.PLAIN, fontSize);
		else if(strs[1].equals("기울임꼴"))
			return new Font(strs[0], Font.ITALIC, fontSize);
		else if(strs[1].equals("굵게"))
			return new Font(strs[0], Font.BOLD, fontSize);
		else if(strs[1].equals("굵은 기울임꼴"))
			return new Font(strs[0], Font.BOLD | Font.ITALIC, fontSize);
		return null;
	}
	
	/**글자 정렬 형식을 받아 문자열로 반환한다.
	 * @see javax.swing.SwingConstants
	 * @param a 변환할 글자 정렬 형식
	 * @return 글자 정렬 형식을 문자열로 변환한 결과
	 */
	public static String alignTypeToString(int a) {
		if(a == SwingConstants.LEFT)
			return "왼쪽";
		else if(a == SwingConstants.CENTER)
			return "가운데";
		else if(a == SwingConstants.RIGHT)
			return "오른쪽";
		return null;
	}
	
	/**문자열을 받아 글자 정렬 형식으로 반환한다. 문자열은 왼쪽, 가운데, 오른쪽 중 하나이다.<br>
	 * 만약 세 항목과 같지 않은 값이 매개변수로 들어오면, 기본값인 왼쪽을 반환한다.
	 * @see javax.swing.SwingConstants
	 * @param s 변환할 문자열
	 * @return 문자열을 글자 정렬 형식로 변환한 결과
	 */
	public static int stringToAlignType(String s) {
		if(s.equals("왼쪽"))
			return SwingConstants.LEFT;
		else if(s.equals("가운데"))
			return SwingConstants.CENTER;
		else if(s.equals("오른쪽"))
			return SwingConstants.RIGHT;
		return SwingConstants.LEFT;
	}
}
